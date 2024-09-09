package models.entities;

import models.enums.PartnerStatus;
import models.enums.TransportType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Partner {
    private final UUID id;
    private String companyName;
    private String businessContact;
    private TransportType transportType;
    private String geographicZone;
    private String specialConditions;
    private PartnerStatus partnerStatus;
    private Date creationDate;
    private final List<Contract> contracts;


    public Partner(String companyName, String businessContact, TransportType transportType, String geographicZone, String specialConditions, PartnerStatus partnerStatus,Date creationDate) {
        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.businessContact = businessContact;
        this.transportType = transportType;
        this.geographicZone = geographicZone;
        this.specialConditions = specialConditions;
        this.partnerStatus = partnerStatus;
        this.creationDate = creationDate;
        this.contracts = new ArrayList<>();
    }

    public Partner(UUID id, String companyName, String businessContact, TransportType transportType, String geographicZone, String specialConditions, PartnerStatus partnerStatus,Date creationDate) {
        this.id = id;
        this.companyName = companyName;
        this.businessContact = businessContact;
        this.transportType = transportType;
        this.geographicZone = geographicZone;
        this.specialConditions = specialConditions;
        this.partnerStatus = partnerStatus;
        this.creationDate = creationDate;
        this.contracts = new ArrayList<>();
    }


    public UUID getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getGeographicZone() {
        return geographicZone;
    }

    public void setGeographicZone(String geographicZone) {
        this.geographicZone = geographicZone;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public PartnerStatus getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(PartnerStatus partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public List<Contract> getContracts() {
        return contracts;
    }

    public void addContract(Contract contract) {
        contracts.add(contract);
    }


}
