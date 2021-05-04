package proj_1;

public class ProblematicTenantException extends Exception{
    public ProblematicTenantException(Person p) {
        super(p.toProblematicTennantException());
    }
}
