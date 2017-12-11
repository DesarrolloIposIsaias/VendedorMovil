package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.State;

import java.util.List;

/**
 * Created by usuario on 11/12/2017.
 */

public interface InterfaceStateDAO {
    State fetchStateById(int stateId);
    List<State> fetchAllStates();
    boolean addState(State state);
    boolean addStates(List<State> states);
    boolean deleteAllStates();
    List<State> searchByFirstLetter(char search);
    boolean isEmpty();
    int getNumberOfStates();
    boolean updateState(State state);
}
