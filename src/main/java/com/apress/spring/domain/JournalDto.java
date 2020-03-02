package com.apress.spring.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalDto {

    private Long id;
    private String title;
    private Date created;
    private String summary;

    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    public JournalDto(Long id, String title, String summary, Date created) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.created = created;
    }

    public JournalDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCreatedAsShort() {
        return format.format(created);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JournalDto{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", created=").append(getCreatedAsShort());
        sb.append(", summary='").append(summary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
