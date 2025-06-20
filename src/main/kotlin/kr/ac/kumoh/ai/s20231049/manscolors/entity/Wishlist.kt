package kr.ac.kumoh.ai.s20231049.manscolors.entity

import jakarta.persistence.*

@Entity
@Table(name = "wishlist")
data class Wishlist(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val wishlistId: Int = 0,
    val uid: Int,
    val pid: Int
)
