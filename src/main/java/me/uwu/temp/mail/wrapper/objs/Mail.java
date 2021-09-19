package me.uwu.temp.mail.wrapper.objs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Mail {
    private final Oid oid;
    private final CreationDate createdAt;
    private final String mailId;
    private final String mailAddressId;
    private final String mailFrom;
    private final String mailSubject;
    private final String mailPreview;
    private final String mailTextOnly;
    private final String mailText;
    private final String mailHtml;
    private final double mailTimestamp;
    private final int mailAttachmentsCount;
    private final Object[] mailAttachments;
}
