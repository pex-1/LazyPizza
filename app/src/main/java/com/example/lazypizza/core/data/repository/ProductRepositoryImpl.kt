package com.example.lazypizza.core.data.repository

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.example.lazypizza.core.domain.repository.ProductsRepository
import com.example.lazypizza.core.data.model.ProductEntity
import com.example.lazypizza.core.data.model.toProduct
import com.example.lazypizza.core.model.Product
import com.example.lazypizza.core.model.ProductType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ProductsRepositoryImpl(
    private val db: FirebaseFirestore
) : ProductsRepository {
    override fun getAllProducts(): Flow<List<Product>> = getAllProductsFlow()

    private fun getAllProductsFlow(): Flow<List<Product>> {
        val collections = ProductType.entries
            .filterNot { it == ProductType.EXTRA_TOPPING }
            .map { type ->
                type.name.toLowerCase(Locale.current) to type
            }

        val flows = collections.map { (collection, type) ->
            callbackFlow {
                val listener = db.collection(collection)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(emptyList())
                            return@addSnapshotListener
                        }

                        val entities = snapshot?.documents.orEmpty().mapNotNull { document ->
                            document.toObject(ProductEntity::class.java)?.copy(id = document.id)
                        }

                        val products = entities.mapNotNull { it.copy(type = type).toProduct() }
                        trySend(products)
                    }

                awaitClose { listener.remove() }
            }
        }

        return combine(flows) { lists ->
            lists.flatMap { it }
        }
    }

    override fun getExtraToppingsFlow(): Flow<List<Product>> =
        callbackFlow {
            val listener = db.collection(ProductType.EXTRA_TOPPING.name.lowercase())
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(emptyList())
                        return@addSnapshotListener
                    }

                    val entities = snapshot?.documents.orEmpty().mapNotNull { document ->
                        document.toObject(ProductEntity::class.java)?.copy(id = document.id)
                    }
                    val products = entities.mapNotNull {
                        it.copy(type = ProductType.EXTRA_TOPPING).toProduct()
                    }
                    trySend(products)
                }

            awaitClose { listener.remove() }
        }

    override fun getPizzaByName(pizzaName: String): Flow<Product.Pizza?> =
        callbackFlow {
            val listener = db.collection(ProductType.PIZZA.name.lowercase())
                .whereEqualTo("name", pizzaName)
                .limit(1)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(null)
                        return@addSnapshotListener
                    }

                    val pizza = snapshot?.documents
                        ?.firstOrNull()
                        ?.toObject(ProductEntity::class.java)
                        ?.copy(id = snapshot.documents.first().id)
                        ?.toProduct() as? Product.Pizza

                    trySend(pizza)
                }

            awaitClose { listener.remove() }
        }

    override fun getProductsByReference(references: List<String>): Flow<List<Product>> =
        flow {
            val products = references.mapNotNull { documentReferences ->
                try {
                    val snapshot = db.document(documentReferences).get().await()
                    val collectionName = snapshot.reference.parent.id

                    snapshot
                        .toObject(ProductEntity::class.java)
                        ?.copy(
                            id = snapshot.id,
                            type = ProductType.valueOf(collectionName.uppercase())
                        )
                        ?.toProduct()

                } catch (e: Exception) {
                    null
                }
            }
            emit(products)
        }
}