package kr.ac.kumoh.ai.s20231049.manscolors.entity

import jakarta.persistence.*

@Entity
@Table(name = "product_attributes")
data class ProductAttribute(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val attributeId: Int = 0,
    val pid: Int,
    val attributeName: String?,
    val attributeValue: String?
)
