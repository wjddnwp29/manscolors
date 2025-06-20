package kr.ac.kumoh.ai.s20231049.manscolors.entity

import jakarta.persistence.*

@Entity
@Table(name = "Reviews")
data class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val reviewId: Int = 0,
    val uid: Int,
    val pid: Int,
    val rating: Int,
    val comment: String?
)

