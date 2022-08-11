package com.heekng.celloct.entity

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class WorkUpdateRequest(
    @Id @GeneratedValue @Column(name = "work_update_request_id")
    val id: Long? = null,

    @Embedded
    val workTime: WorkTime,

    @Column(name = "note")
    val note: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id", nullable = false, unique = true)
    val work: Work,
) : BaseTimeEntity() {

    companion object {
        fun fixture(
            workDate: LocalDate,
            startDate: LocalDateTime,
            endDate: LocalDateTime,
            work: Work,
            note: String? = null
        ): WorkUpdateRequest {
            return WorkUpdateRequest(
                work = work,
                workTime = WorkTime(
                    workDate = workDate,
                    startDate = startDate,
                    endDate = endDate,
                ),
                note = note
            )
        }
    }

}