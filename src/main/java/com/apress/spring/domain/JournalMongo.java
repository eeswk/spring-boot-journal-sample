package com.apress.spring.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalMongo {
    @Id
    private String id;
    private String title;
    private Date created;
    private String summnary;

    @Transient
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    public JournalMongo(String title, String summnary, String date) throws ParseException {
        this.title = title;
        this.summnary = summnary;
        this.created = format.parse(date);
    }

    public JournalMongo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSummnary() {
        return summnary;
    }

    public void setSummnary(String summnary) {
        this.summnary = summnary;
    }

    public String getCreatedAsShort() {
        return format.format(created);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JournalMongo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", created=").append(getCreatedAsShort());
        sb.append(", summnary='").append(summnary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
