package com.example;

import java.util.Iterator;
import java.util.List;

public class FooListResponse {
	
	private List<Foo> foos;

	private String identifier;

	public List<Foo> getFoos() {
		return foos;
	}

	public void setFoos(List<Foo> foos) {
		this.foos = foos;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FooListResponse fooLR = (FooListResponse) o;
        
        boolean equal = fooLR.getFoos().size() == getFoos().size();
        Iterator<Foo> otherIt = fooLR.getFoos().iterator();
        for(Iterator<Foo> it = getFoos().iterator(); it.hasNext() && equal; ) {
        	Foo thisFoo = it.next();
        	Foo otherFoo = otherIt.next();
        	equal &= thisFoo.equals(otherFoo);
        }

        return equal;
    }

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
