package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.Bomb;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.Bulb;

@Component("bulbSolver")
@RequiredArgsConstructor
public class BulbSolver implements Solver {

    private final DataCache dataCache;
    private Bomb bomb;
    private Bulb module;

    @SneakyThrows
    @Override
    public MessageDto solve(String message, String userId) {
        bomb = dataCache.getUserBomb(userId);
        module = (Bulb) dataCache.getUserModule(userId);
        String desc;
        if (message.equals("y") || message.equals("n")) {
            if (module.getCurrentMethod().getName().matches("step2|step3|step6")) {
                module.setBulbGotOff(message.equals("y"));
                desc = (String) module.getCurrentMethod().invoke(this, module.getDescription());
            } else {
                module.setBulbOn(message.equals("y"));
                desc = (String) module.getCurrentMethod().invoke(this);
            }
        } else
            desc = step1(message);
        if (module.getCurrentMethod() == null) {
            dataCache.solveModule(userId);
            return MessageDto.builder().messageType(MessageType.TEXT).text(desc).userId(userId).build();
        } else {
            return MessageDto.builder().messageType(MessageType.TEXT).text(desc + (module.getCurrentMethod().getName().matches("step12|step13") ? "" : "Свет погас? y/n")).userId(userId).build();
        }
    }

    private String step1(String description) throws NoSuchMethodException {
        if (description.contains("on")) {
            if (description.contains("see-through")) {
                module.setFirstStepPressedButton("I");
                module.setCurrentMethod(BulbSolver.class.getMethod("step2", String.class));
                module.setDescription(description);
                return "I ";
            } else {
                module.setFirstStepPressedButton("O");
                module.setCurrentMethod(BulbSolver.class.getMethod("step3", String.class));
                module.setDescription(description);
                return "O ";
            }
        } else
            return "Выкрутите " + step4(description);
    }

    public String step2(String description) throws NoSuchMethodException {
        module.setCurrentMethod(null);
        if (description.contains("red")) {
            module.setSecondOrThirdStepPressedButton("I");
            return "I выкрутите " + step5();
        } else if (description.contains("white")) {
            module.setSecondOrThirdStepPressedButton("O");
            return "O выкрутите " + step6(description);
        } else
            return "Выкрутите " + step7(description);
    }

    public String step3(String description) throws NoSuchMethodException {
        module.setCurrentMethod(null);
        if (description.contains("green")) {
            module.setSecondOrThirdStepPressedButton("I");
            module.setCurrentMethod(BulbSolver.class.getMethod("step6", String.class));
            return "I выкрутите ";
        } else if (description.contains("purple")) {
            module.setSecondOrThirdStepPressedButton("O");
            return "O выкрутите " + step5();
        } else
            return "Выкрутите " + step8(description);
    }

    private String step4(String description) throws NoSuchMethodException {
        if (bomb.hasOneOfIndicators("CAR|IND|MSA|SND"))
            return "I " + step9(description);
        else
            return "O " + step10(description);
    }

    public String step5() {
        if (module.isBulbGotOff())
            return module.getFirstStepPressedButton() + " вкрутите";
        else
            return module.getFirstStepPressedButton().equals("I") ? "O" : "I" + "вкрутите";
    }

    public String step6(String description) {
        module.setCurrentMethod(null);
        if (module.isBulbGotOff())
            return module.getFirstStepPressedButton() + " вкрутите";
        else
            return module.getSecondOrThirdStepPressedButton() + " вкрутите";
    }

    private String step7(String description) throws NoSuchMethodException {
        if (description.contains("green")) {
            module.setRememberedIndicator(bomb.hasIndicator("SIG"));
            return "I " + step11();
        } else if (description.contains("purple")) {
            return "I вкрутите " + step12();
        } else if (description.contains("blue")) {
            module.setRememberedIndicator(bomb.hasIndicator("CLR"));
            return "O " + step11();
        } else {
            return "O вкрутите " + step13();
        }
    }

    private String step8(String description) throws NoSuchMethodException {
        if (description.contains("white")) {
            module.setRememberedIndicator(bomb.hasIndicator("FRQ"));
            return "I " + step11();
        } else if (description.contains("red")) {
            return "I вкрутите " + step13();
        } else if (description.contains("yellow")) {
            module.setRememberedIndicator(bomb.hasIndicator("FRK"));
            return "O " + step11();
        } else {
            return "O вкрутите " + step12();
        }
    }

    private String step9(String description) throws NoSuchMethodException {
        if (description.contains("blue")) {
            return "I " + step14(description);
        } else if (description.contains("green")) {
            return "I вкрутите " + step12();
        } else if (description.contains("yellow")) {
            return "O " + step15(description);
        } else if (description.contains("white")) {
            return "O вкрутите " + step13();
        } else if (description.contains("purple")) {
            return "вкрутите I " + step12();
        } else {
            return "вкрутите O " + step13();
        }
    }

    private String step10(String description) throws NoSuchMethodException {
        if (description.contains("purple")) {
            return "I " + step14(description);
        } else if (description.contains("red")) {
            return "I вкрутите " + step13();
        } else if (description.contains("blue")) {
            return "O " + step15(description);
        } else if (description.contains("yellow")) {
            return "O вкрутите " + step12();
        } else if (description.contains("green")) {
            return "вкрутите I " + step13();
        } else {
            return "вкрутите O " + step12();
        }
    }

    private String step11() {
        if (module.isRememberedIndicator())
            return "I вкрутите";
        else
            return "O вкрутите";
    }

    public String step12() throws NoSuchMethodException {
        if (module.getCurrentMethod() == null) {
            module.setCurrentMethod(BulbSolver.class.getMethod("step12"));
            return "Лампочка горит?";
        } else {
            module.setCurrentMethod(null);
            return module.isBulbOn() ? "I" : "O";
        }
    }

    public String step13() throws NoSuchMethodException {
        if (module.getCurrentMethod() == null) {
            module.setCurrentMethod(BulbSolver.class.getMethod("step13"));
            return "Лампочка горит?";
        } else {
            module.setCurrentMethod(null);
            return module.isBulbOn() ? "O" : "I";
        }
    }

    private String step14(String description) {
        if (description.contains("opaque"))
            return "I вкрутите";
        else
            return "O вкрутите";
    }

    private String step15(String description) {
        if (description.contains("see-through"))
            return "I вкрутите";
        else
            return "O вкрутите";
    }
}
