package kr.ac.kumoh.ai.s20231049.manscolors.entity

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pid: Int = 0,
    val name: String,
    val price: Double,
    val categoryId: Int,
    val description: String?,
    val stockQuantity: Int,
    val image: String?
)
