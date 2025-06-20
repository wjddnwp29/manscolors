package kr.ac.kumoh.ai.s20231049.manscolors.entity

import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uid: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val stylePreference: String?
)
