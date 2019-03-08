package pdf.pdf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepo carRepo;


    public CarRepo getCarRepo() {
        return carRepo;
    }

    public void setCarRepo(CarRepo carRepo) {
        this.carRepo = carRepo;
    }



    @Override
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }
}
