package kr.ac.kumoh.ai.s20231049.manscolors.entity

import jakarta.persistence.*

@Entity
@Table(name = "admin")
data class Admin(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val adminId: Int = 0,
    val name: String,
    val email: String,
    val password: String
)
