package com.heekng.celloct.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(
        name = "work_work_date_staff_id_unique",
        columnNames = ["work_date", "staff_id"]
    )]
)
class Work(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "work_id")
    val id: Long? = null,

    @Embedded
    val workTime: WorkTime,

    @Column(name = "note")
    var note: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    val staff: Staff,

    @OneToOne(mappedBy = "work", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "work_update_request_id")
    var workUpdateRequest: WorkUpdateRequest? = null,
) : BaseTimeEntity() {

    fun addWorkUpdateRequest(workUpdateRequest: WorkUpdateRequest) {
        this.workUpdateRequest = workUpdateRequest
    }

    fun deleteWorkUpdateRequest() {
        this.workUpdateRequest = null
    }

    fun updateWork(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        note: String? = null,
    ) {
        this.workTime.changeWorkTime(startDate, endDate)
        this.note = note
    }
}
