package com.example.proiect.events;

import com.example.proiect.model.Problem;
import org.springframework.context.ApplicationEvent;

public class NewProblemEvent extends ApplicationEvent {

    private Problem problem;


    public NewProblemEvent(Object source) {
        super(source);
    }

    public NewProblemEvent(final Object source, final Problem problem) {
        super(source);
        this.problem = problem;
    }

    public Problem getProblem() {
        return this.problem;
    }
}