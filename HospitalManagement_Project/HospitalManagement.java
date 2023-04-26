import java.io.*;
import java.util.*;
import java.util.Calendar;

class staff {
    String sid, sname, desg, sex;
    int salary;

    void new_staff() {
        Scanner input = new Scanner(System.in);
        System.out.print("id:-");
        sid = input.nextLine();
        System.out.print("name:-");
        sname = input.nextLine();
        System.out.print("desigination:-");
        desg = input.nextLine();
        System.out.print("sex:-");
        sex = input.nextLine();
        System.out.print("salary:-");
        salary = input.nextInt();
    }

    void staff_info() {
        System.out.println(sid + "\t" + sname + "\t" + sex + "\t" + salary);
    }
}

class doctor {
    String did, dname, specilist, appoint, doc_qual;
    int droom;

    void new_doctor() {
        Scanner input = new Scanner(System.in);
        System.out.print("id:-");
        did = input.nextLine();
        System.out.print("name:-");
        dname = input.nextLine();
        System.out.print("specilization:-");
        specilist = input.nextLine();
        System.out.print("work time:-");
        appoint = input.nextLine();
        System.out.print("qualification:-");
        doc_qual = input.nextLine();
        System.out.print("room no.:-");
        droom = input.nextInt();
    }

    void doctor_info() {
        System.out.println(did + "\t" + dname + "  \t" + specilist + "     \t" + appoint + "    \t" + doc_qual
                + "       \t" + droom);
    }
}

class patient {
    String pid, pname, disease, sex, admit_status;
    int age;

    void new_patient() {
        Scanner input = new Scanner(System.in);
        System.out.print("id:-");
        pid = input.nextLine();
        System.out.print("name:-");
        pname = input.nextLine();
        System.out.print("disease:-");
        disease = input.nextLine();
        System.out.print("sex:-");
        sex = input.nextLine();
        System.out.print("admit_status:-");
        admit_status = input.nextLine();
        System.out.print("age:-");
        age = input.nextInt();
    }

    void patient_info() {
        System.out.println(
                pid + "\t" + pname + " \t" + disease + "     \t" + sex + "      \t" + admit_status + "\t" + age);
    }
}

class medical {
    String med_name, med_comp, exp_date;
    int med_cost, count;

    void new_medi() {
        Scanner input = new Scanner(System.in);
        System.out.print("name:-");
        med_name = input.nextLine();
        System.out.print("comp:-");
        med_comp = input.nextLine();
        System.out.print("exp_date:-");
        exp_date = input.nextLine();
        System.out.print("cost:-");
        med_cost = input.nextInt();
        System.out.print("no of unit:-");
        count = input.nextInt();
    }

    void find_medi() {
        System.out.println(med_name + "  \t" + med_comp + "    \t" + exp_date + "     \t" + med_cost);
    }
}

class lab {
    String fecility;
    int lab_cost;

    void new_feci() {
        Scanner input = new Scanner(System.in);
        System.out.print("fecility:-");
        fecility = input.nextLine();
        System.out.print("cost:-");
        lab_cost = input.nextInt();
    }

    void feci_list() {
        System.out.println(fecility + "\t\t" + lab_cost);
    }
}

class fecility // Sorry Facility but do not change the name
{
    String fec_name;

    void add_feci() {
        Scanner input = new Scanner(System.in);
        System.out.print("fecility:-");
        fec_name = input.nextLine();
    }

    void show_feci() {
        System.out.println(fec_name);
    }
}

public class HospitalManagement {
    public static void main(String args[]) {
        String months[] = {
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"
        };
        Calendar calendar = Calendar.getInstance();
        // System.out.println("--------------------------------------------------------------------------------");
        int count1 = 4, count2 = 4, count3 = 4, count4 = 4, count5 = 4, count6 = 4;
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("            *** Welcome to Hospital Management System Project in Java ***");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.print("Date: " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE) + " "
                + calendar.get(Calendar.YEAR));
        System.out.println("\t\t\t\t\t\tTime: " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE)
                + ":" + calendar.get(Calendar.SECOND));
        doctor[] d = new doctor[25];
        patient[] p = new patient[100];
        lab[] l = new lab[20];
        fecility[] f = new fecility[20];
        medical[] m = new medical[100];
        staff[] s = new staff[100];
        int i;
        for (i = 0; i < 25; i++)
            d[i] = new doctor();
        for (i = 0; i < 100; i++)
            p[i] = new patient();
        for (i = 0; i < 20; i++)
            l[i] = new lab();
        for (i = 0; i < 20; i++)
            f[i] = new fecility();
        for (i = 0; i < 100; i++)
            m[i] = new medical();
        for (i = 0; i < 100; i++)
            s[i] = new staff();

        d[0].did = "21";
        d[0].dname = "Dr.Ghanendra";
        d[0].specilist = "ENT";
        d[0].appoint = "5-11AM";
        d[0].doc_qual = "MBBS,MD";
        d[0].droom = 17;
        d[1].did = "32";
        d[1].dname = "Dr.Vikram";
        d[1].specilist = "Physician";
        d[1].appoint = "10-3AM";
        d[1].doc_qual = "MBBS,MD";
        d[1].droom = 45;
        d[2].did = "17";
        d[2].dname = "Dr.Rekha";
        d[2].specilist = "Surgeon";
        d[2].appoint = "8-2AM";
        d[2].doc_qual = "BDM";
        d[2].droom = 8;
        d[3].did = "33";
        d[3].dname = "Dr.Pramod";
        d[3].specilist = "Artho";
        d[3].appoint = "10-4PM";
        d[3].doc_qual = "MBBS,MS";
        d[3].droom = 40;

        p[0].pid = "12";
        p[0].pname = "Pankaj";
        p[0].disease = "Cancer";
        p[0].sex = "Male";
        p[0].admit_status = "y";
        p[0].age = 30;
        p[1].pid = "13";
        p[1].pname = "Sumit";
        p[1].disease = "Cold";
        p[1].sex = "Male";
        p[1].admit_status = "y";
        p[1].age = 23;
        p[2].pid = "14";
        p[2].pname = "Alok";
        p[2].disease = "Maleriya";
        p[2].sex = "Male";
        p[2].admit_status = "y";
        p[2].age = 45;
        p[3].pid = "15";
        p[3].pname = "Ravi";
        p[3].disease = "Diabetes";
        p[3].sex = "Male";
        p[3].admit_status = "y";
        p[3].age = 25;

        m[0].med_name = "Corex";
        m[0].med_comp = "Cino pvt";
        m[0].exp_date = "9-5-16";
        m[0].med_cost = 55;
        m[0].count = 8;
        m[1].med_name = "Nytra";
        m[1].med_comp = "Ace pvt";
        m[1].exp_date = "4-4-15";
        m[1].med_cost = 500;
        m[1].count = 5;
        m[2].med_name = "Brufa";
        m[2].med_comp = "Reckitt";
        m[2].exp_date = "12-7-17";
        m[2].med_cost = 50;
        m[2].count = 56;
        m[3].med_name = "Pride";
        m[3].med_comp = "DDF pvt";
        m[3].exp_date = "12-4-12";
        m[3].med_cost = 1100;
        m[3].count = 100;

        l[0].fecility = "X-ray     ";
        l[0].lab_cost = 800;
        l[1].fecility = "CT Scan   ";
        l[1].lab_cost = 1200;
        l[2].fecility = "OR Scan   ";
        l[2].lab_cost = 500;
        l[3].fecility = "Blood Bank";
        l[3].lab_cost = 50;

        f[0].fec_name = "Ambulance";
        f[1].fec_name = "Admit Facility ";
        f[2].fec_name = "Canteen";
        f[3].fec_name = "Emergency";

        s[0].sid = "22";
        s[0].sname = "Prakash";
        s[0].desg = "Worker";
        s[0].sex = "Male";
        s[0].salary = 5000;
        s[1].sid = "23";
        s[1].sname = "Komal";
        s[1].desg = "Nurse";
        s[1].sex = "Female";
        s[1].salary = 2000;
        s[2].sid = "24";
        s[2].sname = "Raju";
        s[2].desg = "Worker";
        s[2].sex = "Male";
        s[2].salary = 5000;
        s[3].sid = "25";
        s[3].sname = "Rani";
        s[3].desg = "Nurse";
        s[3].sex = "Female";
        s[3].salary = 20000;

        Scanner input = new Scanner(System.in);
        int choice, j, c1, status = 1, s1 = 1, s2 = 1, s3 = 1, s4 = 1, s5 = 1, s6 = 1;
        while (status == 1) {
            System.out.println("\n                                    MAIN MENU");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("1.Doctos  2. Patients  3.Medicines  4.Laboratories  5. Facilities  6. Staff ");
            System.out.println("-----------------------------------------------------------------------------------");
            choice = input.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    System.out.println("                      **DOCTOR SECTION**");
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    s1 = 1;
                    while (s1 == 1) {
                        System.out.println("1.Add New Entry\n2.Existing Doctors List");
                        c1 = input.nextInt();
                        switch (c1) {
                            case 1: {
                                d[count1].new_doctor();
                                count1++;
                                break;
                            }
                            case 2: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("id \t Name\t Specilist \t Timing \t Qualification \t Room No.");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count1; j++) {
                                    d[j].doctor_info();
                                }
                                break;
                            }
                        }
                        System.out.println("\nReturn to Back Press 1 and for Main Menu Press 0");
                        s1 = input.nextInt();
                    }
                    break;
                }
                case 2: {
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    System.out.println("                     **PATIENT SECTION**");
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    s2 = 1;
                    while (s2 == 1) {
                        System.out.println("1.Add New Entry\n2.Existing Patients List");
                        c1 = input.nextInt();
                        switch (c1) {
                            case 1: {
                                p[count2].new_patient();
                                count2++;
                                break;
                            }
                            case 2: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("id \t Name \t Disease \t Gender \t Admit Status \t Age");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count2; j++) {
                                    p[j].patient_info();
                                }
                                break;
                            }
                        }
                        System.out.println("\nReturn to Back Press 1 and for Main Menu Press 0");
                        s2 = input.nextInt();
                    }
                    break;
                }
                case 3: {
                    s3 = 1;
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    System.out.println("                     **MEDICINE SECTION**");
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    while (s3 == 1) {
                        System.out.println("1.Add New Entry\n2. Existing Medicines List");
                        c1 = input.nextInt();
                        switch (c1) {
                            case 1: {
                                m[count3].new_medi();
                                count3++;
                                break;
                            }
                            case 2: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("Name \t Company \t Expiry Date \t Cost");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count3; j++) {
                                    m[j].find_medi();
                                }
                                break;
                            }
                        }
                        System.out.println("\nReturn to Back Press 1 and for Main Menu Press 0");
                        s3 = input.nextInt();
                    }
                    break;
                }
                case 4: {
                    s4 = 1;
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    System.out.println("                    **LABORATORY SECTION**");
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    while (s4 == 1) {
                        System.out.println("1.Add New Entry \n2.Existing Laboratories List");
                        c1 = input.nextInt();
                        switch (c1) {
                            case 1: {
                                l[count4].new_feci();
                                count4++;
                                break;
                            }
                            case 2: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("Fecilities\t\t Cost");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count4; j++) {
                                    l[j].feci_list();
                                }
                                break;
                            }
                        }
                        System.out.println("\nReturn to Back Press 1 and for Main Menu Press 0");
                        s4 = input.nextInt();
                    }
                    break;
                }
                case 5: {
                    s5 = 1;
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    System.out.println("          **HOSPITAL FACILITY SECTION**");
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    while (s5 == 1) {
                        System.out.println("1.Add New Facility\n2.Existing Fecilities List");
                        c1 = input.nextInt();
                        switch (c1) {
                            case 1: {
                                f[count5].add_feci();
                                count5++;
                                break;
                            }
                            case 2: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("Hospital  Facility are:");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count5; j++) {
                                    f[j].show_feci();
                                }
                                break;
                            }
                        }
                        System.out.println("\nReturn to Back Press 1 and for Main Menu Press 0");
                        s5 = input.nextInt();
                    }
                    break;
                }
                case 6: {
                    s6 = 1;
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    System.out.println("                       **STAFF SECTION**");
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    while (s6 == 1) {
                        String a = "nurse", b = "worker", c = "security";
                        System.out.println(
                                "1.Add New Entry \n2.Existing Nurses List\n3.Existing Workers List \n4.Existing Security List");
                        c1 = input.nextInt();
                        switch (c1) {
                            case 1: {
                                s[count6].new_staff();
                                count6++;
                                break;
                            }
                            case 2: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("id \t Name \t Gender \t Salary");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count6; j++) {
                                    if (a.equals(s[j].desg))
                                        s[j].staff_info();
                                }
                                break;
                            }
                            case 3: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("id \t Name \t Gender \t Salary");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count6; j++) {
                                    if (b.equals(s[j].desg))
                                        s[j].staff_info();
                                }
                                break;
                            }
                            case 4: {
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                System.out.println("id \t Name \t Gender \t Salary");
                                System.out.println(
                                        "--------------------------------------------------------------------------------");
                                for (j = 0; j < count6; j++) {
                                    if (c.equals(s[j].desg))
                                        s[j].staff_info();
                                }
                                break;
                            }
                        }
                        System.out.println("\nReturn to Back Press 1 and for Main Menu Press 0");
                        s6 = input.nextInt();
                    }
                    break;
                }
                default: {
                    System.out.println(" You Have Enter Wrong Choice!!!");
                }
            }
            System.out.println("\nReturn to MAIN MENU Press 1");
            status = input.nextInt();
        }
    }
}