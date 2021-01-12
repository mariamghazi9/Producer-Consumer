package model;

import java.util.List;

public interface Source {

    boolean update (Producer machine);
    List<Producer> getReadyMachines();
    List<Machine> getMachines();
}
