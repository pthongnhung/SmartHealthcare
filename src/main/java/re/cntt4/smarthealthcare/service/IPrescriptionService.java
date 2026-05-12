package re.cntt4.smarthealthcare.service;

import re.cntt4.smarthealthcare.entity.Prescription;

import java.util.List;

public interface IPrescriptionService {

    List<Prescription> getAllPrescriptions();

    void dispenseMedicine(Integer prescriptionId);
}