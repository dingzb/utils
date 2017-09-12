package cc.idiary.utils.office;

public enum OfficeType {

    //TODO 先只做 word
    /**
     * MicroSoft Word 2007 [*.docx]
     */
    MS_WORD07("Word.Application"),
    /**
     * MicroSoft Excel 2007 [*.xlsx]
     */
    MS_EXCEL07(""),
    /**
     * MicroSoft PowerPoint 2007 [*.pptx]
     */
    MS_PPT07("");

    private String value = null;

    OfficeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
