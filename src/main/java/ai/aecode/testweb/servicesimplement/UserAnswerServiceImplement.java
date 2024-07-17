package ai.aecode.testweb.servicesimplement;

import ai.aecode.testweb.entities.*;
import ai.aecode.testweb.repositories.*;
import ai.aecode.testweb.services.IUserAnswerService;
import ai.aecode.testweb.services.IUserResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserAnswerServiceImplement implements IUserAnswerService {
    @Autowired
    private IUserAnswerRepository uaR;
    @Autowired
    private IUserProfileRepository upR;

    @Autowired
    private IPersonTypeRepository ptR;

    @Autowired
    private ISkillRepository sR;

    @Override
    public void insert(UserAnswer useranswer) {

        // Buscar el perfil del usuario por correo electrónico
        String email = useranswer.getUserProfile().getUser_email();
        UserProfile userProfile = upR.findByUserEmail(email);

        if (userProfile != null) {
            // Obtener las respuestas anteriores y eliminarlas
            List<UserAnswer> existingUserAnswers = uaR.findByUserProfile(userProfile);
            if (!existingUserAnswers.isEmpty()) {
                uaR.deleteAll(existingUserAnswers);
            }

            // Asociar el perfil del usuario existente a las nuevas respuestas
            useranswer.setUserProfile(userProfile);
        } else {
            throw new RuntimeException("No se encontró el perfil del usuario");
        }

        // Guardar las nuevas respuestas del usuario
        uaR.save(useranswer);
    }

    @Override
    public List<UserAnswer> list() {
        return uaR.findAll();
    }

    @Override
    public void delete(int id_useranswer) {
        uaR.deleteById(id_useranswer);
    }

    @Override
    public UserAnswer listId(int id_useranswer) {
        return uaR.findById(id_useranswer).orElse(new UserAnswer());
    }

    @Override
    public UserResult processUserAnswersAndSaveResult(String email) {
        // Obtener el perfil del usuario por email
        UserProfile userProfile = upR.findByUserEmail(email);

        // Obtener las respuestas del usuario
        List<UserAnswer> userAnswers = uaR.findByUserProfile(userProfile);



        // Calcular los resultados
        double Manager = 0.0;
        double Developer = 0.0;
        double Executor = 0.0;

        // Obtener el signo zodiacal del usuario
        ZodiacSign zodiacSign = userProfile.getZodiacsign();
        String signName = zodiacSign.getSign_name();

        // Obtener la fecha de nacimiento del usuario
        LocalDate birthday = userProfile.getUser_birthday();
        // Calcular la edad del usuario
        int age = Period.between(birthday, LocalDate.now()).getYears();

        // Definir los puntajes basados en la edad del usuario
        double birthday_manager = 0.0;
        double birthday_developer = 0.0;
        double birthday_executor = 0.0;

        //obtener el genero del usuario:
        String gender= userProfile.getUser_gender();
        //Definir los puntajes basados en el genero del usuario
        double gender_manager = 0.0;
        double gender_developer = 0.0;
        double gender_executor = 0.0;

        if(signName.equals("Aries")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 70;
                birthday_developer = 75;
                birthday_executor = 60;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 80;
                birthday_developer = 65;
                birthday_executor = 65;
            } else if (age > 35) {
                birthday_manager = 90;
                birthday_developer = 60;
                birthday_executor = 75;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 75;
                gender_developer = 80;
                gender_executor = 70;
            }else if(gender.equals("Femenino")){
                gender_manager = 85;
                gender_developer = 75;
                gender_executor = 65;
            }
        } else if (signName.equals("Tauro")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 70;
                birthday_developer = 80;
                birthday_executor = 65;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 80;
                birthday_developer = 75;
                birthday_executor = 70;
            } else if (age > 35) {
                birthday_manager = 90;
                birthday_developer = 65;
                birthday_executor = 80;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 75;
                gender_developer = 80;
                gender_executor = 65;
            }else if(gender.equals("Femenino")){
                gender_manager = 85;
                gender_developer = 75;
                gender_executor = 70;
            }

        }else if (signName.equals("Géminis")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 55;
                birthday_developer = 85;
                birthday_executor = 55;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 70;
                birthday_developer = 75;
                birthday_executor = 60;
            } else if (age > 35) {
                birthday_manager = 85;
                birthday_developer = 60;
                birthday_executor = 75;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 70;
                gender_developer = 90;
                gender_executor = 65;
            }else if(gender.equals("Femenino")){
                gender_manager = 80;
                gender_developer = 70;
                gender_executor = 60;
            }

        }else if (signName.equals("Cáncer")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 65;
                birthday_developer = 70;
                birthday_executor = 65;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 75;
                birthday_developer = 65;
                birthday_executor = 70;
            } else if (age > 35) {
                birthday_manager = 90;
                birthday_developer = 55;
                birthday_executor = 80;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 70;
                gender_developer = 70;
                gender_executor = 60;
            }else if(gender.equals("Femenino")){
                gender_manager = 85;
                gender_developer = 75;
                gender_executor = 70;
            }


        }else if (signName.equals("Leo")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 75;
                birthday_developer = 70;
                birthday_executor = 65;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 85;
                birthday_developer = 60;
                birthday_executor = 70;
            } else if (age > 35) {
                birthday_manager = 95;
                birthday_developer = 55;
                birthday_executor = 80;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 80;
                gender_developer = 70;
                gender_executor = 65;
            }else if(gender.equals("Femenino")){
                gender_manager = 90;
                gender_developer = 75;
                gender_executor = 70;
            }


        }else if (signName.equals("Virgo")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 70;
                birthday_developer = 85;
                birthday_executor = 70;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 80;
                birthday_developer = 80;
                birthday_executor = 75;
            } else if (age > 35) {
                birthday_manager = 90;
                birthday_developer = 70;
                birthday_executor = 80;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 85;
                gender_developer = 80;
                gender_executor = 75;
            }else if(gender.equals("Femenino")){
                gender_manager = 75;
                gender_developer = 85;
                gender_executor = 70;
            }

        }else if (signName.equals("Libra")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 60;
                birthday_developer = 75;
                birthday_executor = 65;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 75;
                birthday_developer = 70;
                birthday_executor = 70;
            } else if (age > 35) {
                birthday_manager = 90;
                birthday_developer = 60;
                birthday_executor = 75;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 75;
                gender_developer = 85;
                gender_executor = 70;
            }else if(gender.equals("Femenino")){
                gender_manager = 85;
                gender_developer =75;
                gender_executor = 65;
            }

        }else if (signName.equals("Escorpio")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 65;
                birthday_developer = 75;
                birthday_executor = 65;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 75;
                birthday_developer = 65;
                birthday_executor = 70;
            } else if (age > 35) {
                birthday_manager = 90;
                birthday_developer = 60;
                birthday_executor = 80;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 70;
                gender_developer = 70;
                gender_executor = 60;
            }else if(gender.equals("Femenino")){
                gender_manager = 85;
                gender_developer = 75;
                gender_executor = 70;
            }

        }else if (signName.equals("Sagitario")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 70;
                birthday_developer = 80;
                birthday_executor = 65;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 75;
                birthday_developer = 70;
                birthday_executor = 70;
            } else if (age > 35) {
                birthday_manager = 85;
                birthday_developer = 65;
                birthday_executor = 75;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 75;
                gender_developer = 80;
                gender_executor = 65;
            }else if(gender.equals("Femenino")){
                gender_manager = 85;
                gender_developer = 70;
                gender_executor = 70;
            }

        }else if (signName.equals("Capricornio")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 75;
                birthday_developer = 80;
                birthday_executor = 70;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 85;
                birthday_developer = 70;
                birthday_executor = 75;
            } else if (age > 35) {
                birthday_manager = 95;
                birthday_developer = 65;
                birthday_executor = 80;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 80;
                gender_developer = 80;
                gender_executor = 65;
            }else if(gender.equals("Femenino")){
                gender_manager = 90;
                gender_developer = 75;
                gender_executor = 70;
            }

        }else if (signName.equals("Acuario")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 50;
                birthday_developer = 90;
                birthday_executor = 60;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 65;
                birthday_developer = 85;
                birthday_executor = 65;
            } else if (age > 35) {
                birthday_manager = 80;
                birthday_developer = 70;
                birthday_executor = 80;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 65;
                gender_developer = 90;
                gender_executor = 70;
            }else if(gender.equals("Femenino")){
                gender_manager = 75;
                gender_developer = 65;
                gender_executor = 70;
            }

        }else if (signName.equals("Piscis")) {
            // Asignar los puntajes según la edad del usuario
            if (age >= 18 && age <= 25) {
                birthday_manager = 70;
                birthday_developer = 65;
                birthday_executor = 65;
            } else if (age > 25 && age <= 35) {
                birthday_manager = 75;
                birthday_developer = 60;
                birthday_executor = 70;
            } else if (age > 35) {
                birthday_manager = 90;
                birthday_developer = 55;
                birthday_executor = 75;
            }

            // Asignar los puntajes según el genero del usuario
            if(gender.equals("Masculino")){
                gender_manager = 75;
                gender_developer = 70;
                gender_executor = 60;
            }else if(gender.equals("Femenino")){
                gender_manager = 85;
                gender_developer = 70;
                gender_executor = 65;
            }
        }

        double percentage_age=0.55;
        double percentage_gender=0.45;

        //valores de los personajes segun edad y genero
        double value_manager_pesos=(birthday_manager*percentage_age)+(gender_manager*percentage_gender);
        double value_developer_pesos=(birthday_developer*percentage_age)+(gender_developer*percentage_gender);
        double value_executor_pesos=(birthday_executor*percentage_age)+(gender_executor*percentage_gender);


        for (UserAnswer userAnswer : userAnswers) {
            for (Answer answer : userAnswer.getAnswer()) {
                Manager += answer.getValue_manager();
                Developer += answer.getValue_developer();
                Executor += answer.getValue_executor();
            }
        }
        double totalManager =value_manager_pesos * Manager;
        double totalDeveloper = value_developer_pesos * Developer;
        double totalExecutor = value_executor_pesos * Executor;

        double totalSum = totalManager + totalDeveloper + totalExecutor;
        double percentageManager = ((totalManager*100)/(totalSum));
        double percentageDeveloper = ((totalDeveloper*100)/(totalSum));
        double percentageExecutor = ((totalExecutor*100)/(totalSum));;

        // Redondear a un número entero utilizando Math.round
        int roundedPercentageManager = (int) Math.round(percentageManager);
        int roundedPercentageDeveloper = (int) Math.round(percentageDeveloper);
        int roundedPercentageExecutor = (int) Math.round(percentageExecutor);

        // Asignar los valores redondeados de vuelta a los porcentajes
        percentageManager = roundedPercentageManager;
        percentageDeveloper = roundedPercentageDeveloper;
        percentageExecutor = roundedPercentageExecutor;
        // Convertir los valores a enteros
        int finalPercentageManager = (int) percentageManager;
        int finalPercentageDeveloper = (int) percentageDeveloper;
        int finalPercentageExecutor = (int) percentageExecutor;

        /*
        // Redondear a un decimal y asegurar un decimal fijo utilizando DecimalFormat
        BigDecimal bdPercentageManager = new BigDecimal(percentageManager).setScale(0, RoundingMode.HALF_UP);
        BigDecimal bdPercentageDeveloper = new BigDecimal(percentageDeveloper).setScale(0, RoundingMode.HALF_UP);
        BigDecimal bdPercentageExecutor = new BigDecimal(percentageExecutor).setScale(0, RoundingMode.HALF_UP);

        percentageManager = bdPercentageManager.doubleValue();
        percentageDeveloper = bdPercentageDeveloper.doubleValue();
        percentageExecutor = bdPercentageExecutor.doubleValue();
        */


        // Determinar el tipo de persona (PersonType)
        PersonType personType=null;
        String personTypeDescription="";
        int d=20;
        double max_percentage = Math.max(percentageManager, Math.max(percentageDeveloper, percentageExecutor));
        if(max_percentage==percentageManager){
            if(max_percentage-percentageDeveloper<=d){
                if(max_percentage-percentageExecutor<=d){
                    personType = ptR.findByPersonTypeName("Equilibrado");
                    personTypeDescription = "Persona versatil que se adapta facilmente a cualquier que tiene habilodades " +
                            "como pensamiento critico, logico, etc en buenos porcentajes desarrollados. ";
                }else{
                    personType = ptR.findByPersonTypeName("Gestor - Desarrollador");
                    personTypeDescription = "Combina liderazgo y creatividad técnica, ideal para roles de gestión de" +
                            " proyectos innovadores y coordinación de equipos. Fortalecer la ejecución aumenta la versatilidad y eficacia.";
                }
            }else{
                if(max_percentage-percentageExecutor<=d){
                    personType = ptR.findByPersonTypeName("Gestor - Ejecutor");
                    personTypeDescription = "Combina liderazgo y ejecución precisa, ideal para roles de gestión de " +
                            "proyectos y supervisión de operaciones. Fortalecer el desarrollo aumenta la adaptabilidad y eficacia.";
                }else{
                    personType = ptR.findByPersonTypeName("Gestor");
                    personTypeDescription = "Combina liderazgo y organización, ideal para roles de dirección de proyectos " +
                            "y coordinación de equipos. Fortalecer las habilidades técnicas y de ejecución aumenta la versatilidad y eficacia.";
                }
            }
        }
        if(max_percentage==percentageDeveloper){
            if(max_percentage-percentageManager<=d){
                if(max_percentage-percentageExecutor<=d){
                    personType = ptR.findByPersonTypeName("Equilibrado");
                    personTypeDescription = "Persona versatil que se adapta facilmente a cualquier que tiene habilodades " +
                            "como pensamiento critico, logico, etc en buenos porcentajes desarrollados. ";
                }else{
                    personType = ptR.findByPersonTypeName("Gestor - Desarrollador");
                    personTypeDescription = "Combina liderazgo y creatividad técnica, ideal para roles de gestión de" +
                            " proyectos innovadores y coordinación de equipos. Fortalecer la ejecución aumenta la versatilidad y eficacia.";
                }
            }else{
                if(max_percentage-percentageExecutor<=d){
                    personType = ptR.findByPersonTypeName("Ejecutor - Desarrollador");
                    personTypeDescription = "Combina liderazgo, creatividad técnica y gestión, ideal para dirigir" +
                            " proyectos innovadores y coordinar equipos. Fortalecer la ejecución mejora la eficacia y versatilidad.";
                }else{
                    personType = ptR.findByPersonTypeName("Desarrollador");
                    personTypeDescription = "Se destaca por creatividad técnica e innovación. Ideal para roles en " +
                            "desarrollo de nuevas tecnologías y programación avanzada. Fortalecer gestión y ejecución aumenta la versatilidad y liderazgo.";
                }
            }
        }
        if(max_percentage==percentageExecutor){
            if(max_percentage-percentageDeveloper<=d){
                if(max_percentage-percentageManager<=d){
                    personType = ptR.findByPersonTypeName("Equilibrado");
                    personTypeDescription = "Persona versatil que se adapta facilmente a cualquier que tiene habilodades " +
                            "como pensamiento critico, logico, etc en buenos porcentajes desarrollados. ";
                }else{
                    personType = ptR.findByPersonTypeName("Ejecutor - Desarrollador");
                    personTypeDescription = "Combina liderazgo, creatividad técnica y gestión, ideal para dirigir" +
                            " proyectos innovadores y coordinar equipos. Fortalecer la ejecución mejora la eficacia y versatilidad.";
                }
            }else{
                if(max_percentage-percentageManager<=d){
                    personType = ptR.findByPersonTypeName("Gestor - Ejecutor");
                    personTypeDescription = "Combina liderazgo y ejecución precisa, ideal para roles de gestión de " +
                            "proyectos y supervisión de operaciones. Fortalecer el desarrollo aumenta la adaptabilidad y eficacia.";
                }else{
                    personType = ptR.findByPersonTypeName("Ejecutor");
                    personTypeDescription = "Se centra en la precisión técnica y la implementación. Ideal para roles " +
                            "operativos y técnicos. Fortalecer gestión y desarrollo aumenta la versatilidad y liderazgo en proyectos complejos.";
                }
            }
        }

        // Crear dos listas para los dos grupos de habilidades
        List<Skill> hemisferioDerecho = new ArrayList<>();
        List<Skill> hemisferioIzquierdo = new ArrayList<>();

        // Dividir las habilidades en los dos grupos y asignar el puntaje máximo
        for (UserAnswer userAnswer : userAnswers) {
            for (Answer answer : userAnswer.getAnswer()) {
                if(max_percentage==percentageManager) {
                    int maxValue = answer.getValue_skill_manager();
                    Skill skill = sR.findBySkillName(answer.getQuestion().getSkill().getSkill_name());
                    if (skill != null) {
                        skill.setFinal_score(maxValue);
                        if (isHemisferioDerecho(skill.getSkill_name())) {
                            hemisferioDerecho.add(skill);
                        } else if (isHemisferioIzquierdo(skill.getSkill_name())) {
                            hemisferioIzquierdo.add(skill);
                        }
                    }
                }else if(max_percentage==percentageDeveloper){
                    int maxValue = answer.getValue_skill_developer();
                    Skill skill = sR.findBySkillName(answer.getQuestion().getSkill().getSkill_name());
                    if (skill != null) {
                        skill.setFinal_score(maxValue);
                        if (isHemisferioDerecho(skill.getSkill_name())) {
                            hemisferioDerecho.add(skill);
                        } else if (isHemisferioIzquierdo(skill.getSkill_name())) {
                            hemisferioIzquierdo.add(skill);
                        }
                    }
                }else if(max_percentage==percentageExecutor) {
                    int maxValue = answer.getValue_skill_executor();
                    Skill skill = sR.findBySkillName(answer.getQuestion().getSkill().getSkill_name());
                    if (skill != null) {
                        skill.setFinal_score(maxValue);
                        if (isHemisferioDerecho(skill.getSkill_name())) {
                            hemisferioDerecho.add(skill);
                        } else if (isHemisferioIzquierdo(skill.getSkill_name())) {
                            hemisferioIzquierdo.add(skill);
                        }
                    }
                }
            }
        }

        // Ordenar cada grupo de habilidades por el puntaje final en orden descendente
        hemisferioDerecho.sort((s1, s2) -> Integer.compare(s2.getFinal_score(), s1.getFinal_score()));
        hemisferioIzquierdo.sort((s1, s2) -> Integer.compare(s2.getFinal_score(), s1.getFinal_score()));

        // Seleccionar las tres habilidades con mayor puntaje de cada grupo
        List<Skill> topSkills = new ArrayList<>();
        topSkills.addAll(hemisferioDerecho.stream().limit(3).collect(Collectors.toList()));
        topSkills.addAll(hemisferioIzquierdo.stream().limit(3).collect(Collectors.toList()));

        // Crear y devolver el objeto UserResult
        UserResult userResult = new UserResult();
        userResult.setUserProfile(userProfile);
        userResult.setPersonType(personType);
        userResult.setResult_manager(finalPercentageManager);
        userResult.setResult_developer(finalPercentageDeveloper);
        userResult.setResult_executor(finalPercentageExecutor);
        userResult.setPerson_type_description(personTypeDescription);

        // Agregar las skills al UserResult
        userResult.setSkill(new HashSet<>(topSkills));

        return userResult;
    }
    // Método auxiliar para determinar si una habilidad pertenece al Hemisferio Derecho
    private boolean isHemisferioDerecho(String skillName) {
        return skillName.equals("Creatividad") || skillName.equals("Resiliencia") ||
                skillName.equals("Pensamiento Crítico") || skillName.equals("Comunicación Efectiva") ||
                skillName.equals("Adaptabilidad") || skillName.equals("Coordinación y Organización");
    }

    // Método auxiliar para determinar si una habilidad pertenece al Hemisferio Izquierdo
    private boolean isHemisferioIzquierdo(String skillName) {
        return skillName.equals("Pensamiento Lógico") || skillName.equals("Precisión y Atención al Detalle") ||
                skillName.equals("Resolución de Problemas Técnicos") || skillName.equals("Manejo de Herramientas") ||
                skillName.equals("Planificación Estratégica") || skillName.equals("Gestión de Recursos");
    }
}
