package kr.ac.kumoh.ai.s20231049.manscolors.repository

import kr.ac.kumoh.ai.s20231049.manscolors.dto.ProductDetailDto
import kr.ac.kumoh.ai.s20231049.manscolors.dto.WishlistItemDto
import kr.ac.kumoh.ai.s20231049.manscolors.entity.Product
import kr.ac.kumoh.ai.s20231049.manscolors.entity.Review
import kr.ac.kumoh.ai.s20231049.manscolors.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface FashionRepository : JpaRepository<Product, Int> {

    /** 3.1 사용자 프로필 조회 */
    @Query("""
        SELECT u FROM User u WHERE u.uid = :uid
    """)
    fun findUserByUid(@Param("uid") uid: Int): User?

    /** 3.2 제품 필터 검색 */
    @Query("""
        SELECT p FROM Product p
        JOIN ProductAttribute a ON p.pid = a.pid
        WHERE (:stretch IS NULL OR a.attributeName = 'stretch' AND a.attributeValue = :stretch)
          AND (:recommendedSeason IS NULL OR a.attributeName = 'recommended_season' AND a.attributeValue = :recommendedSeason)
    """)
    fun searchProducts(
        @Param("stretch") stretch: String?,
        @Param("recommendedSeason") recommendedSeason: String?
    ): List<Product>

    /** 3.3 패션 제품 상세 조회 */
    @Query("""
        SELECT new kr.ac.kumoh.ai.s20231049.manscolors.dto.ProductDetailDto(
            p.name,
            c.categoryName,
            p.price,
            p.stockQuantity,
            p.image,
            r.rating,
            r.comment,
            a.attributeName,
            a.attributeValue,
            i.rank,
            i.viewCount
        )
        FROM Product p
        JOIN Category c ON p.categoryId = c.categoryId
        LEFT JOIN Review r ON p.pid = r.pid
        LEFT JOIN ProductAttribute a ON p.pid = a.pid
        LEFT JOIN ItemRanking i ON p.pid = i.pid
        WHERE p.pid = :pid
    """)
    fun findProductDetail(@Param("pid") pid: Int): List<ProductDetailDto>

    /** 3.4 제품 찜 여부 확인 */
    @Query("""
        SELECT COUNT(w) > 0 FROM Wishlist w WHERE w.uid = :uid AND w.pid = :pid
    """)
    fun existsInWishlist(@Param("uid") uid: Int, @Param("pid") pid: Int): Boolean

    /** 3.4 제품 찜하기 - 삭제 */
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Wishlist w WHERE w.uid = :uid AND w.pid = :pid
    """)
    fun removeWishlist(@Param("uid") uid: Int, @Param("pid") pid: Int): Int

    /** 3.4 제품 찜하기 - 등록 */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
        INSERT INTO wishlist(uid, pid) VALUES (:uid, :pid)
    """)
    fun addWishlist(@Param("uid") uid: Int, @Param("pid") pid: Int): Int

    /** 3.5 찜한 제품 목록 조회 */
    @Query("""
        SELECT new kr.ac.kumoh.ai.s20231049.manscolors.dto.WishlistItemDto(
            p.pid, p.name, p.image
        )
        FROM Wishlist w
        JOIN Product p ON w.pid = p.pid
        WHERE w.uid = :uid
    """)
    fun findWishlistByUid(@Param("uid") uid: Int): List<WishlistItemDto>

    /** 3.6 리뷰 작성 */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
        INSERT INTO Reviews(uid, pid, rating,comment) VALUES (:uid, :pid, :rating, :comment)
    """)
    fun addReview(
        @Param("uid") uid: Int,
        @Param("pid") pid: Int,
        @Param("rating") rating: Int,
        @Param("comment") comment: String
    ): Int


    /** 3.7 특정 제품 리뷰 조회 */
    @Query("""
        SELECT r FROM Review r WHERE r.pid = :pid
    """)
    fun findReviewsByPid(@Param("pid") pid: Int): List<Review>

    /** 3.8 패션 제품 등록 */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
    INSERT INTO products(name, price, category_id, description, stock_quantity, image)
    VALUES (:name, :price, :categoryId, :description, :stockQuantity, :image)
""")
    fun addProduct(
        @Param("name") name: String,
        @Param("price") price: Double,
        @Param("categoryId") categoryId: Int,
        @Param("description") description: String?,
        @Param("stockQuantity") stockQuantity: Int,
        @Param("image") image: String?
    ): Int

    /** 3.9 패션 제품 수정 */
    @Modifying
    @Transactional
    @Query("""
    UPDATE Product p
    SET p.name = :name, p.price = :price, p.categoryId = :categoryId, p.description = :description, p.stockQuantity = :stockQuantity, p.image = :image
    WHERE p.pid = :pid
""")
    fun updateProduct(
        @Param("pid") pid: Int,
        @Param("name") name: String,
        @Param("price") price: Double,
        @Param("categoryId") categoryId: Int,
        @Param("description") description: String?,
        @Param("stockQuantity") stockQuantity: Int,
        @Param("image") image: String?
    ): Int


    /** 3.9 패션 제품 삭제 */
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Product p WHERE p.pid = :pid
    """)
    fun deleteProductById(@Param("pid") pid: Int): Int

    /** 3.10 전체 리뷰 조회 */
    @Query("""
        SELECT r FROM Review r
    """)
    fun findAllReviews(): List<Review>

    /** 3.10 리뷰 삭제 */
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Review r WHERE r.id = :reviewId
    """)
    fun deleteReviewById(@Param("reviewId") reviewId: Int): Int
}
