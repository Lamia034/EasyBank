package services;
import dto.Agency;
import implementations.AgencyImplementation;
import java.util.Optional;

public class AgencyService {
  //  AgencyImplementation agencyI = new AgencyImplementation();
private final AgencyImplementation agencyI;
public AgencyService(AgencyImplementation agencyI){
    this.agencyI = agencyI;
}

     public Optional<Agency> addagency(Agency agency) {
        return agencyI.add(agency);
    }


}

