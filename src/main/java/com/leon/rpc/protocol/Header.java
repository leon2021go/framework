package com.leon.rpc.protocol;

/**
 * 请求头
 *
 */
public class Header {
    private Long messageId;
    private short magic;
    private byte version;
    private byte extraInfo;
    private Integer size;

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(byte extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
