package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.MemoryModule;

import java.util.Arrays;

import static ru.mas.ktane_bot.model.CommonValues.*;

@Component("memorySolver")
@RequiredArgsConstructor
public class MemorySolver implements Solver {

    private MemoryModule module = new MemoryModule();

    private final DataCache dataCache;

    private static final String PRESS = "Нажмите на ";

    @SneakyThrows
    @Override
    public MessageDto solve(String message, String userId) {
        module = (MemoryModule) dataCache.getUserModule(userId);
        var splitted = Arrays.stream(message.split("")).toList();
        var result = switch (module.getPositions().size()) {
            case 0 -> switch (splitted.get(0)) {
                case ONE, TWO -> {
                    setValueAndPosition(userId, splitted.get(2), TWO);
                    yield PRESS + splitted.get(2);
                }
                case THREE -> {
                    setValueAndPosition(userId, splitted.get(3), THREE);
                    yield PRESS + splitted.get(3);
                }
                case FOUR -> {
                    setValueAndPosition(userId, splitted.get(4), FOUR);
                    yield PRESS + splitted.get(4);
                }
                default -> throw new Exception(); // TODO validate
            };
            case 1 -> switch (splitted.get(0)) {
                case ONE -> {
                    setValueAndPosition(userId, FOUR, String.valueOf(splitted.subList(1, 5).indexOf(FOUR) + 1));
                    yield PRESS + FOUR;
                }
                case TWO, FOUR -> {
                    setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().getFirst())),
                            module.getPositions().getFirst());
                    yield PRESS + splitted.get(Integer.parseInt(module.getPositions().getFirst()));
                }
                case THREE -> {
                    setValueAndPosition(userId, splitted.get(1), ONE);
                    yield PRESS + splitted.get(1);
                }
                default -> throw new Exception();
            };
            case 2 -> switch (splitted.get(0)) {
                case ONE -> {
                    setValueAndPosition(userId, module.getValues().get(1),
                            String.valueOf(splitted.subList(1, 5).indexOf(module.getValues().get(1))));
                    yield PRESS + module.getValues().get(1);
                }
                case TWO -> {
                    setValueAndPosition(userId, module.getValues().get(0),
                            String.valueOf(splitted.subList(1, 5).indexOf(module.getValues().get(0))));
                    yield PRESS + module.getValues().getFirst();
                }
                case THREE -> {
                    setValueAndPosition(userId, splitted.get(3), THREE);
                    yield PRESS + splitted.get(3);
                }
                case FOUR -> {
                    setValueAndPosition(userId, FOUR, String.valueOf(splitted.subList(1, 5).indexOf(FOUR) + 1));
                    yield PRESS + FOUR;
                }
                default -> throw new Exception();
            };
            case 3 -> switch (splitted.get(0)) {
                case ONE -> {
                    setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().getFirst())),
                            module.getPositions().getFirst());
                    yield PRESS + splitted.get(Integer.parseInt(module.getPositions().getFirst()));
                }
                case TWO -> {
                    setValueAndPosition(userId, splitted.get(1), ONE);
                    yield PRESS + splitted.get(1);
                }
                case THREE, FOUR -> {
                    setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().get(1))),
                            module.getPositions().get(1));
                    yield PRESS + splitted.get(Integer.parseInt(module.getPositions().get(1)));
                }
                default -> throw new Exception();
            };
            case 4 -> {
                dataCache.solveModule(userId);
                yield switch (splitted.getFirst()) {
                    case ONE -> PRESS + module.getValues().get(0);
                    case TWO -> PRESS + module.getValues().get(1);
                    case THREE -> PRESS + module.getValues().get(3);
                    case FOUR -> PRESS + module.getValues().get(2);
                    default -> throw new Exception();
                };
            }
            default -> throw new Exception();
        };
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }

    private void setValueAndPosition(String userId, String value, String position) {
        module.putPosition(position);
        module.putValue(value);
        dataCache.saveUserModule(userId, module);
    }
}
