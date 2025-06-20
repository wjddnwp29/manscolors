package kr.ac.kumoh.ai.s20231049.manscolors.service

import kr.ac.kumoh.ai.s20231049.manscolors.dto.ProductDetailDto
import kr.ac.kumoh.ai.s20231049.manscolors.dto.UserProfileDto
import kr.ac.kumoh.ai.s20231049.manscolors.dto.WishlistItemDto
import kr.ac.kumoh.ai.s20231049.manscolors.entity.Product
import kr.ac.kumoh.ai.s20231049.manscolors.entity.Review
import kr.ac.kumoh.ai.s20231049.manscolors.repository.FashionRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class FashionService(
    private val repository: FashionRepository

) {
    // 3.1 사용자 프로필 조회 + 찜 목록 포함
    fun getUserProfile(uid: Int): ResponseEntity<UserProfileDto> {
        val user = repository.findUserByUid(uid)
            ?: throw NoSuchElementException("사용자 ID $uid 를 찾을 수 없습니다.")
        val wishlist = repository.findWishlistByUid(uid)
        val profile = UserProfileDto(
            uid = user.uid,
            name = user.name,
            email = user.email,
            stylePreference = user.stylePreference,
            wishlist = wishlist
        )
        return ResponseEntity.ok(profile)
    }

    // 3.2 패션 제품 단순 목록
    fun getSimpleProductList(): List<Product> = repository.findAll()

    // 3.3 제품 상세 정보
    fun getProductDetails(pid: Int): ResponseEntity<List<ProductDetailDto>> {
        return ResponseEntity.ok(repository.findProductDetail(pid))
    }

    // 3.4 찜 등록/삭제
    fun toggleWishlist(uid: Int, pid: Int): ResponseEntity<String> {
        return if (repository.existsInWishlist(uid, pid)) {
            repository.removeWishlist(uid, pid)
            ResponseEntity.ok("찜 목록에서 제거되었습니다.")
        } else {
            repository.addWishlist(uid, pid)
            ResponseEntity.ok("찜 목록에 추가되었습니다.")
        }
    }

    // 3.5 찜 목록 조회
    fun getWishlist(uid: Int): ResponseEntity<Map<String, List<WishlistItemDto>>> {
        val wishlist = repository.findWishlistByUid(uid)
        return ResponseEntity.ok(mapOf("wishlist" to wishlist))
    }

    // 3.6 리뷰 작성
    fun addReview(uid: Int, pid: Int, rating: Int, comment: String): ResponseEntity<String> {
        return try {
            repository.addReview(uid, pid, rating, comment)
            ResponseEntity.ok("리뷰가 등록되었습니다.")
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(500).body("리뷰 등록 실패: ${e.message}")
        }
    }


    // 3.7 제품 리뷰 조회
    fun getReviews(pid: Int): ResponseEntity<Map<String, List<Review>>> {
        val reviews = repository.findReviewsByPid(pid)
        return ResponseEntity.ok(mapOf("reviews" to reviews))
    }


    // 3.8 제품 등록
    fun addProduct(product: Product): ResponseEntity<String> {
        repository.addProduct(
            name = product.name,
            price = product.price,
            categoryId = product.categoryId,
            description = product.description,
            stockQuantity = product.stockQuantity,
            image = product.image
        )
        return ResponseEntity.ok("제품이 등록되었습니다.")
    }

    // 3.9 제품 수정
    fun updateProduct(pid: Int, product: Product): ResponseEntity<String> {
        repository.updateProduct(
            pid = pid,
            name = product.name,
            price = product.price,
            categoryId = product.categoryId,
            description = product.description,
            stockQuantity = product.stockQuantity,
            image = product.image
        )
        return ResponseEntity.ok("제품 정보가 수정되었습니다.")
    }


    // 3.9 제품 삭제
    fun deleteProduct(pid: Int): ResponseEntity<String> {
        repository.deleteProductById(pid)
        return ResponseEntity.ok("제품이 삭제되었습니다.")
    }

    // 3.10 전체 리뷰 조회
    fun getAllReviews(): ResponseEntity<List<Review>> {
        return ResponseEntity.ok(repository.findAllReviews())
    }

    // 3.10 리뷰 삭제
    fun deleteReview(reviewId: Int): ResponseEntity<String> {
        repository.deleteReviewById(reviewId)
        return ResponseEntity.ok("리뷰가 삭제되었습니다.")
    }
}
