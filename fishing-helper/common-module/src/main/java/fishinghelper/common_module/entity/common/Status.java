package fishinghelper.common_module.entity.common;

public enum Status {
    APPROVED("одобренно"),NOT_APPROVED("неодобренно"),DELETED("удалено"),IN_PROCESSING("в обработке");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public static String getStatus(Status statusEnum){
        return statusEnum.status;
    }
}
