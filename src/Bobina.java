public class Bobina {
    private int number_of_loops_in_one_length;

    public double conductor_length;
    public double boltage;
    public double conductor_section_radius;
    public double coil_section_radius;
    public double coil_length;

    public double radio_final = -1;

    public Bobina(double length, double conductor_section_radius, double coil_section_radius, double coil_length){
        this.conductor_length = length;
        this.conductor_section_radius = conductor_section_radius;
        this.coil_section_radius = coil_section_radius;
        this.coil_length = coil_length;

        this.boltage = 12.;

        this.number_of_loops_in_one_length = (int)Math.floor(coil_length / (coil_section_radius * 2));
    }

    public Bobina(double length, double voltage, double conductor_section_radius, double coil_section_radius, double coil_length){
        this.conductor_length = length;
        this.conductor_section_radius = conductor_section_radius;
        this.coil_section_radius = coil_section_radius;
        this.coil_length = coil_length;
        this.boltage = voltage;

        this.number_of_loops_in_one_length = (int)Math.floor(coil_length / (coil_section_radius * 2));
    }

    public Bobina(Bobina other){
        this.conductor_length = other.conductor_length;
        this.conductor_section_radius = other.conductor_section_radius;
        this.coil_section_radius = other.coil_section_radius;
        this.coil_length = other.coil_length;
        this.boltage = other.boltage;
        this.number_of_loops_in_one_length = other.number_of_loops_in_one_length;
    }

    public double coilResistance(){
        // resistivity * length / section
        return Constants.copper_resistivity * ((this.conductor_length)/(Math.PI * this.conductor_section_radius * this.conductor_section_radius));
    }

    public double current(){
        // V / R
        return (this.boltage/this.coilResistance());
    }

    public double numberOfTurns(){
        // Length / perimeter = Length / 2*pi*radius
        // return (this.conductor_length/(2 * Math.PI * this.coil_section_radius));

        double total = 0;
        double conductor_length_copy = this.conductor_length;
        double current_loop_radius = this.coil_section_radius;
        double current_loop_length;
        while(conductor_length_copy > 0){
            current_loop_length = 2 * Math.PI * current_loop_radius;
            conductor_length_copy -= (current_loop_length * this.number_of_loops_in_one_length);
            if(conductor_length_copy < 0){
                total += this.number_of_loops_in_one_length - (-conductor_length_copy / current_loop_length);
            }else{
                total += this.number_of_loops_in_one_length;
            }

            current_loop_radius += 2 * this.conductor_section_radius;
        }
        return total;
    }

    public double magneticField(){
        // return ((Constants.vacuum_permeability * this.current())/(2 * this.coil_section_radius))*this.numberOfTurns();
        double total_field = 0.;
        double current_number_of_loops;
        
        double conductor_length_copy = this.conductor_length;
        double current_loop_radius = this.coil_section_radius;
        double current_loop_length;
        while(conductor_length_copy > 0){
            current_loop_length = 2 * Math.PI * current_loop_radius;
            conductor_length_copy -= (current_loop_length * this.number_of_loops_in_one_length);
            if(conductor_length_copy < 0){
                current_number_of_loops = this.number_of_loops_in_one_length - (-conductor_length_copy / current_loop_length);
            }else{
                current_number_of_loops = this.number_of_loops_in_one_length;
            }

            total_field += ((Constants.vacuum_permeability * this.current())/(2 * current_loop_radius)) * current_number_of_loops;
            current_loop_radius += 2 * this.conductor_section_radius;
        }
        radio_final = current_loop_radius;
        return total_field;
    }

    @Override
    public String toString() {
        return "Bobina{" +
                "conductor_length=" + conductor_length +
                ", boltage=" + boltage +
                ", conductor_section_radius=" + conductor_section_radius +
                ", coil_length= " + coil_length +
                ", coil_section_radius=" + coil_section_radius +
                ", coil_section_radius_final=" + radio_final +
                ", coilResistance=" + coilResistance() +
                ", current=" + current() +
                ", turns=" + numberOfTurns() +
                ", magneticField=" + magneticField() +
                '}';
    }

    public void print() {
        System.out.println(this);
    }
}
