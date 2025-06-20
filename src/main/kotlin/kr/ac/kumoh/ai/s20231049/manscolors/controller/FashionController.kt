package kr.ac.kumoh.ai.s20231049.manscolors.controller

import kr.ac.kumoh.ai.s20231049.manscolors.dto.ProductDetailDto
import kr.ac.kumoh.ai.s20231049.manscolors.dto.UserProfileDto
import kr.ac.kumoh.ai.s20231049.manscolors.entity.Product
import kr.ac.kumoh.ai.s20231049.manscolors.service.FashionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/fashion")
class FashionController(
    private val service: FashionService
) {
    // 8.1 사용자 프로필 + 찜 목록 조회
    @GetMapping("/users/{uid}")
    fun getUserProfile(@PathVariable uid: Int): ResponseEntity<UserProfileDto> {
        return service.getUserProfile(uid)
    }

    // 8.2 제품 검색 (stretch, recommended_season, name)
    @GetMapping("/products")
    fun getSimpleProducts(): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(service.getSimpleProductList())
    }

    // 8.3 제품 상세 정보 조인된 정보 반환
    @GetMapping("/products/details/{pid}")
    fun getProductDetails(@PathVariable pid: Int): ResponseEntity<List<ProductDetailDto>> {
        return service.getProductDetails(pid)
    }


    // 8.4 제품 찜하기 등록/해제
    @PostMapping("/wishlist")
    fun toggleWishlist(@RequestParam uid: Int, @RequestParam pid: Int): ResponseEntity<String> {
        return service.toggleWishlist(uid, pid)
    }

    // 8.5 찜 목록 조회
    @GetMapping("/wishlist/{uid}")
    fun getWishlist(@PathVariable uid: Int) = service.getWishlist(uid)

    // 8.6 리뷰 작성
    @PostMapping("/reviews/{pid}/{uid}")
    fun writeReview(
        @PathVariable pid: Int,
        @PathVariable uid: Int,
        @RequestParam rating: Int,
        @RequestParam comment: String
    ): ResponseEntity<String> {
        return service.addReview(uid, pid, rating, comment)
    }


    // 8.7 리뷰 조회
    @GetMapping("/reviews/{pid}")
    fun getReviews(@PathVariable pid: Int) = service.getReviews(pid)


    // 8.8 제품 등록 (관리자)
    @PostMapping("/admin/products")
    fun createProduct(@RequestBody product: Product): ResponseEntity<String> {
        return service.addProduct(product)
    }

    // 8.9 제품 수정 (관리자)
    @PutMapping("/admin/products/{pid}")
    fun updateProduct(@PathVariable pid: Int, @RequestBody product: Product): ResponseEntity<String> {
        return service.updateProduct(pid, product)
    }


    // 8.9 제품 삭제 (관리자)
    @DeleteMapping("/admin/products/{pid}")
    fun deleteProduct(@PathVariable pid: Int): ResponseEntity<String> {
        return service.deleteProduct(pid)
    }

    // 8.10 전체 리뷰 목록 (관리자용)
    @GetMapping("/admin/reviews")
    fun getAllReviews() = service.getAllReviews()

    // 8.10 리뷰 삭제 (관리자용)
    @DeleteMapping("/admin/reviews/{reviewId}")
    fun deleteReview(@PathVariable reviewId: Int): ResponseEntity<String> {
        return service.deleteReview(reviewId)
    }
}
