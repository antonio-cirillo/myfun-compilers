package it.unisa.nodes.interfaces;

import it.unisa.visitors.Visitor;

public interface Visitable {

    Object accept(Visitor visitor) throws Exception;

}
