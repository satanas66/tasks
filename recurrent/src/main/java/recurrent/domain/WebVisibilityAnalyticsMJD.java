package recurrent.domain;

import java.util.List;

public interface WebVisibilityAnalyticsMJD {

    void updateKPISEG404ByIdentifier(String identifier, Integer value);

    void updateKPISEG400ByIdentifier(String identifier, String value);

    void deleteRegisterByIdentifier(String identifier);

    List<String> findAllClientCodesWithEcommerceCheckoutLink();
}
