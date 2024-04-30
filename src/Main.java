import java.io.*;

public class Main {
    private static double min_conductor_length = 1.;                    // meters
    private static double max_conductor_length = 178.;                   // meters
    private static double step_conductor_length = 1.;                   // meters

    private static double min_voltage = 12.;                             // volts
    private static double max_voltage = 12.;                            // volts
    private static double step_voltage = 1.;                            // volts

    private static double min_conductor_section_redius = 0.1 / 1000;    // meters
    private static double max_conductor_section_redius = 0.1 / 1000;     // meters
    private static double step_conductor_section_redius = 0.05 / 1000;  // meters

    private static double min_coil_length = 2. / 1000;                         // meters
    private static double max_coil_length = 20. / 1000;                        // meters
    private static double step_coil_length = 1. / 1000;                        // meters

    private static double max_amps = 2;

    public static boolean condicionPrint(Bobina coil) {
        if(coil.current() > max_amps){

        } else {

        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        // solo el dia de hoy:
        /*
        __________             __                                                                                                                      __
        \______   \_____      |__|____        ____  ____   ____       ___________   ____   _____ _____         ____   ____   ____   ________________ _/  |_  ___________
         |     ___/\__  \     |  \__  \     _/ ___\/  _ \ /    \    _/ ___\_  __ \_/ __ \ /     \\__  \       / ___\_/ __ \ /    \_/ __ \_  __ \__  \\   __\/  _ \_  __ \
         |    |     / __ \_   |  |/ __ \_   \  \__(  <_> )   |  \   \  \___|  | \/\  ___/|  Y Y  \/ __ \_    / /_/  >  ___/|   |  \  ___/|  | \// __ \|  | (  <_> )  | \/
         |____|    (____  /\__|  (____  /    \___  >____/|___|  /    \___  >__|    \___  >__|_|  (____  /    \___  / \___  >___|  /\___  >__|  (____  /__|  \____/|__|
                        \/\______|    \/         \/           \/         \/            \/      \/     \/    /_____/      \/     \/     \/           \/
         */

        Bobina coil_gan = new Bobina(min_conductor_length, min_voltage,  min_conductor_section_redius, 5. / 1000, min_coil_length);
        double current_amps;
        double current_magnetic_field;
        double max_capped_magnetic_field=0;
        double max_uncapped_magnetic_field=0;
        Bobina max_capped = coil_gan, max_uncapped = coil_gan;

        for(double current_conductor_length = min_conductor_length; current_conductor_length <= max_conductor_length; current_conductor_length += step_conductor_length){
            coil_gan.conductor_length = current_conductor_length;
            for(double current_voltage = min_voltage; current_voltage <= max_voltage; current_voltage += step_voltage){
                coil_gan.boltage = current_voltage;
                for(double current_conductor_section_radius = min_conductor_section_redius; current_conductor_section_radius <= max_conductor_section_redius; current_conductor_section_radius += step_conductor_section_redius){
                    coil_gan.conductor_section_radius = current_conductor_section_radius;
                    for(double current_coil_length = min_coil_length; current_coil_length <= max_coil_length; current_coil_length += step_coil_length){
                        coil_gan.coil_length = current_coil_length;
                        System.out.println("entrando for");

                        current_amps = coil_gan.current();
                        current_magnetic_field = coil_gan.magneticField();
                        System.out.println("pito");
                        if(current_amps <= max_amps){
                            if(current_magnetic_field > max_capped_magnetic_field){
                                System.out.println("new capped");
                                max_capped = new Bobina(coil_gan);
                                max_capped_magnetic_field = max_capped.magneticField();
                                coil_gan.print();
                            }
                        }else{
                            if(current_magnetic_field > max_uncapped_magnetic_field){
                                System.out.println("new uncapped");
                                max_uncapped = new Bobina(coil_gan);
                                max_uncapped_magnetic_field = max_uncapped.magneticField();
                                coil_gan.print();
                            }
                        }



                        //if (condicionPrint(coil_gan))
                        //    System.out.println(coil_gan);
                    }
                }
            }
        }


        System.out.println("------Resultaciones------");
        System.out.println("Tope de jefe mi equipo uncapped");
        max_uncapped.print();
        System.out.println("Finorza mi rey capped");
        max_capped.print();;


    }
}