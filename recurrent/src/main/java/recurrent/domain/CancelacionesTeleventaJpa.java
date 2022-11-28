package recurrent.domain;

public interface CancelacionesTeleventaJpa {

    String getIdAccountByClientCode(Integer clientCode);

    String getCoAmbiComerByClientCode(Integer clientCode);

    Object[] getProjectionFromF_Datos_ConctoByClientCode(Integer clientCode);

    String getSectorCodeByActivityCode(Integer activityCode);

    String getSectorTextBySectorCode(String sectorCode);

    String getAlianzaByClientCode(Integer clientCode);

    String getStatusPayByClientCode(Integer clientCode);

    String getLiveClientByClientCode(Integer clientCode);

    String lastChanceWinByClientCode(Integer clientCode);
}
