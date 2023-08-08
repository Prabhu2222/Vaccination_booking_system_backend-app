package com.example.VaccinationBookingSystem.service;

import com.example.VaccinationBookingSystem.DTO.RequestDto.CenterRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.CenterResponseDto;
import com.example.VaccinationBookingSystem.ENUM.CenterType;
import com.example.VaccinationBookingSystem.model.VaccinationCenter;
import com.example.VaccinationBookingSystem.repository.DoctorRepository;
import com.example.VaccinationBookingSystem.repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationCenterService {
    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;
    @Autowired
    DoctorRepository doctorRepository;
    public CenterResponseDto addCenter(CenterRequestDto centerRequestDto) {

        //convert request dto to->entity
        VaccinationCenter center=new VaccinationCenter();
        center.setCenterName(centerRequestDto.getCenterName());
        center.setCenterType(centerRequestDto.getCenterType());
        center.setAddress(centerRequestDto.getAddress());
        //save entity to repository
        VaccinationCenter savedCenter=vaccinationCenterRepository.save(center);

        //convert entity to responseDto
        //we have to extract info from savedCenter entity/object

        CenterResponseDto responseDto=new CenterResponseDto();
        responseDto.setCenterName(savedCenter.getCenterName());
        responseDto.setCenterType(savedCenter.getCenterType());
        responseDto.setAddress(savedCenter.getAddress());

        return responseDto;
    }

    public List<String> getDoctorsAtCenterType(CenterType centerType) {
        List<VaccinationCenter>centerList=vaccinationCenterRepository.findAll();
        List<Integer>centerIdList=new ArrayList<>();
        for(VaccinationCenter center:centerList){
            if(center.getCenterType().equals(centerType))
                centerIdList.add(center.getId());
        }
        List<String> docNamesList=new ArrayList<>();
        for(int ele:centerIdList){
            List<String > names=doctorRepository.getList(ele);
            docNamesList.addAll(names);
        }
        return docNamesList;
    }


    public List<String> getCentersWithHighDocCount() {
        List<VaccinationCenter>centerList=vaccinationCenterRepository.findAll();
        List<String> ans=new ArrayList<>();
        int count=1;
        for(VaccinationCenter ele:centerList){
            if(ele.getDoctors().size()>count){
                ans.clear();
                ans.add(ele.getCenterName());
            }else if(ele.getDoctors().size()==count){
                ans.add(ele.getCenterName());
            }
        }
        return ans;
    }

    public List<String> getCenterTypeWithHighDocCount(CenterType centerType) {
        List<VaccinationCenter>centerList=vaccinationCenterRepository.findAll();
        List<String> ans=new ArrayList<>();
        int count=1;
        for(VaccinationCenter ele:centerList){
            if(ele.getCenterType().equals(centerType)){
                if(ele.getDoctors().size()>count){
                    ans.clear();
                    ans.add(ele.getCenterName());
                }else if(ele.getDoctors().size()==count){
                    ans.add(ele.getCenterName());
                }
            }

        }
        return ans;
    }
}
