package com.gamejigi.attend.model.dto;

public class NoticeDTO {
    private Long id;
    private String writeday;
    private String title;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriteday() {
        return writeday;
    }

    public void setWriteday(String writeday) {
        this.writeday = writeday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
