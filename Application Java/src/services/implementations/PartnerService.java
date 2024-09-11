package services.implementations;

import dao.implementations.PartnershipDao;
import models.entities.Partner;
import models.enums.PartnerStatus;
import models.enums.TransportType;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class PartnerService {
    private final PartnershipDao partnershipDao;

    public PartnerService() {
        this.partnershipDao = new PartnershipDao();
    }


    public void addPartner(String companyName, String businessContact, TransportType transportType, String geographicZone,
                           String specialConditions, PartnerStatus partnerStatus, Date creationDate) {
        Partner partner = new Partner(companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
        partnershipDao.addPartner(partner);
    }


    public void modifyPartner(UUID partnerId, String companyName, String businessContact, TransportType transportType,
                              String geographicZone, String specialConditions, PartnerStatus partnerStatus, Date creationDate) {
        partnershipDao.modifyPartner(partnerId, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
    }


    public void deletePartner(UUID partnerId) {
        partnershipDao.deletePartner(partnerId);
    }


    public List<Partner> listAllPartners() {
        return partnershipDao.findAll();
    }


    public Partner getPartnerById(UUID partnerId) {
        return partnershipDao.getPartnerById(partnerId);
    }
}
