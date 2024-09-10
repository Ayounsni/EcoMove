package services;

import dao.implementations.ContractDao;
import models.entities.Contract;
import models.enums.ContractStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class ContractService {
    private final ContractDao contractDao;



    public ContractService() {
        this.contractDao = new ContractDao();
    }

    public void add(Date startDate, Date endDate, BigDecimal specialRate, String agreementConditions, boolean renewable, ContractStatus contractStatus, UUID partnerId) {
        Contract contract = new Contract(startDate, endDate ,specialRate, agreementConditions, renewable, contractStatus, partnerId);
        contractDao.addContract(contract);

    }

    public void edit(UUID contractId, Date startDate, Date endDate, BigDecimal specialRate, String agreementConditions, boolean renewable, ContractStatus contractStatus){
        contractDao.updateContract(contractId, startDate, endDate, specialRate, agreementConditions, renewable, contractStatus);
    }

    public  void deleteContract(UUID contractId){
        contractDao.deleteContract(contractId);
    }
    public void display(){
        contractDao.displayContracts();
    }
    public Contract getContractById(UUID contractId){
        return contractDao.getContractById(contractId);
    }
}
