package kr.ac.kumoh.ai.s20231049.manscolors.dto/**
 * 사용자 정보 + 찜 목록을 포함한 응답 DTO
 * - /api/users/{uid} 응답에 사용
 */
data class UserProfileDto(
    val uid: Int,
    val name: String,
    val email: String,
    val stylePreference: String?,
    val wishlist: List<WishlistItemDto>
)

/**
 * 찜한 제품 목록에 포함될 단순 제품 정보
 */
data class WishlistItemDto(
    val pid: Int,
    val name: String,
    val image: String?
)
