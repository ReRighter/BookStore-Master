package org.zdd.bookstore.model.entity;

import javax.persistence.*;
import java.util.Date;


@Table
public class VisitRecord {
    @Id
    @Column(name = "visit_record_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int visitRecordPk;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "book_category")
    private int bookCategory;
    @Column(name = "visit_time")
    private int visitTime;

    //public boolean isSaved=false;


    public int getVisitRecordPk() {
        return visitRecordPk;
    }

    public void setVisitRecordPk(int visitRecordPk) {
        this.visitRecordPk = visitRecordPk;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(int bookCategory) {
        this.bookCategory = bookCategory;
    }

    public int getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(int visitTime) {
        this.visitTime = visitTime;
    }
}
