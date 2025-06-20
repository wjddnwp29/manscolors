package kr.ac.kumoh.ai.s20231049.manscolors.dto

import java.math.BigDecimal

data class ProductDetailDto(
    val productName: String,
    val categoryName: String,
    val price: Double,
    val stockQuantity: Int,
    val image: String?,
    val rating: Int?,
    val comment: String?,
    val attributeName: String?,
    val attributeValue: String?,
    val rank: Int?,
    val viewCount: Int?
)

