package kr.ac.kumoh.ai.s20231049.manscolors.entity

import jakarta.persistence.*

@Entity
@Table(name = "item_ranking")
data class ItemRanking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val rankId: Int = 0,
    val pid: Int,
    val rank: Int,
    val viewCount: Int
)
