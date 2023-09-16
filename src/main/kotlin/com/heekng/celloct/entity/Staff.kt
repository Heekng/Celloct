package com.heekng.celloct.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(
        name = "staff_member_id_shop_id_unique",
        columnNames = ["member_id", "shop_id"]
    )]
)
class Staff(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    val id: Long? = null,

    @Column(name = "employment_date", nullable = false)
    var employmentDate: LocalDate = LocalDate.now(),

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    val shop: Shop,

    @OneToMany(mappedBy = "staff", cascade = [CascadeType.REMOVE])
    val works: MutableList<Work> = mutableListOf(),
) : BaseTimeEntity() {

    companion object {
        fun fixture(
            member: Member,
            shop: Shop,
            name: String
        ): Staff {
            val staff = Staff(
                member = member,
                shop = shop,
                name = name,
            )
            shop.staffList.add(staff)
            member.staffList.add(staff)
            return staff
        }
    }

    fun changeEmploymentDate(changeEmploymentDate: LocalDate) {
        this.employmentDate = changeEmploymentDate
    }

    fun updateInfo(name: String) {
        this.name = name
    }
}
