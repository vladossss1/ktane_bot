package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KeyboardModule extends BombModule {

    private static final List<String> firstColumn = List.of(
            "CAACAgIAAxkBAAIcU2fEp0N9hBTfS1frSJZ9wYN25c_yAAKYYAACMbgpSlgHFI_J55CRNgQ",
            "CAACAgIAAxkBAAIcXGfEp-A7dawSUVWIPx6vHRFCqojeAAL8ZgACVF4pSrqs9oLsHqpgNgQ",
            "CAACAgIAAxkBAAIcXWfEp-Cf-KFRkQTma_FuTvfUmvWcAAIucAAC9ZUoStosrp3vZddbNgQ",
            "CAACAgIAAxkBAAIcYWfEp-nmVvpp9TtE1l34IV70-wS8AALHZQACoJwoStZsBiKg27TVNgQ",
            "CAACAgIAAxkBAAIcY2fEp-vpp4X64W_8R0y32_BfWa8EAAKlawACFxIhSqpVZvhQWxVVNgQ",
            "CAACAgIAAxkBAAIcZWfEp-1aCPF6750VXbCAEXKcXM-UAAK2agAC3LAgSgSg8Kx9qSIbNgQ",
            "CAACAgIAAxkBAAIcZ2fEp-1urCaN4VKiMbIhfkVpj9cBAALsaQAC8Y8gSnIa64jnnfidNgQ");
    private static final List<String> secondColumn = List.of(
            "CAACAgIAAxkBAAIdKGfEwWa6jnBSJ8DUcNfIiPdyI2HdAAImdQACTFspSqntSjPbN817NgQ",
            "CAACAgIAAxkBAAIcU2fEp0N9hBTfS1frSJZ9wYN25c_yAAKYYAACMbgpSlgHFI_J55CRNgQ",
            "CAACAgIAAxkBAAIcZ2fEp-1urCaN4VKiMbIhfkVpj9cBAALsaQAC8Y8gSnIa64jnnfidNgQ",
            "CAACAgIAAxkBAAIdCmfEwLKdTbiCN5JKHdwXJPAMfhr4AAI9awACFUQgSvLG7LmKIsWXNgQ",
            "CAACAgIAAxkBAAIdDGfEwL3_YNgz-DyjVV0B_xEGQCvsAALNagAC2EghSoaJXC-z2NHJNgQ",
            "CAACAgIAAxkBAAIcZWfEp-1aCPF6750VXbCAEXKcXM-UAAK2agAC3LAgSgSg8Kx9qSIbNgQ",
            "CAACAgIAAxkBAAIdDmfEwM8SxyRmXOfofjqPX4TwmgfwAAJFZAACYsEpSutJUEmxapN7NgQ");
    private static final List<String> thirdColumn = List.of(
            "CAACAgIAAxkBAAIdEGfEwN0Kf68eNFqlL090Bcr9rYruAAIZbQACKvggSlRsE-4OnIDXNgQ",
            "CAACAgIAAxkBAAIdEmfEwOelPKAMEGQ9r7GaA8psv-S3AAKicAACbxAgStf7fK6ddylpNgQ",
            "CAACAgIAAxkBAAIdCmfEwLKdTbiCN5JKHdwXJPAMfhr4AAI9awACFUQgSvLG7LmKIsWXNgQ",
            "CAACAgIAAxkBAAIdFGfEwPCgX5wIuOXBo9KCMzSWJIb6AAI_YAACWSIoSsq5b95ir3yYNgQ",
            "CAACAgIAAxkBAAIdFmfEwPlMhqSJ_oSB3m6fcGCVeXmFAAKAZwACahgoSoqNiV3T25nSNgQ",
            "CAACAgIAAxkBAAIcXWfEp-Cf-KFRkQTma_FuTvfUmvWcAAIucAAC9ZUoStosrp3vZddbNgQ",
            "CAACAgIAAxkBAAIdDGfEwL3_YNgz-DyjVV0B_xEGQCvsAALNagAC2EghSoaJXC-z2NHJNgQ");
    private static final List<String> fourthColumn = List.of(
            "CAACAgIAAxkBAAIdGGfEwQFQLfHbk3rWjVQ2aKPh0u9OAAKyZwAC9achSlnT1z_qj7z_NgQ",
            "CAACAgIAAxkBAAIdGmfEwRSvlYXb3YZm0iwzwsOmWIHaAAIobgAC1sUpSlRaSTRsb70rNgQ",
            "CAACAgIAAxkBAAIdHGfEwR3OdN3PpIYouTOC9aMxigdAAALqbgACLQUhSngd1kiUqYb5NgQ",
            "CAACAgIAAxkBAAIcY2fEp-vpp4X64W_8R0y32_BfWa8EAAKlawACFxIhSqpVZvhQWxVVNgQ",
            "CAACAgIAAxkBAAIdFGfEwPCgX5wIuOXBo9KCMzSWJIb6AAI_YAACWSIoSsq5b95ir3yYNgQ",
            "CAACAgIAAxkBAAIdDmfEwM8SxyRmXOfofjqPX4TwmgfwAAJFZAACYsEpSutJUEmxapN7NgQ",
            "CAACAgIAAxkBAAIdHmfEwSf-uTun_8TrSQ-MgwOJCHkPAAIqawACubQgSs1GdWwDP2gZNgQ");
    private static final List<String> fifthColumn = List.of(
            "CAACAgIAAxkBAAIdIGfEwTbg-appXDaMQlBDR0hnMiYKAAK1ZQACqlsoSm-z-aF9uN6BNgQ",
            "CAACAgIAAxkBAAIdHmfEwSf-uTun_8TrSQ-MgwOJCHkPAAIqawACubQgSs1GdWwDP2gZNgQ",
            "CAACAgIAAxkBAAIdHGfEwR3OdN3PpIYouTOC9aMxigdAAALqbgACLQUhSngd1kiUqYb5NgQ",
            "CAACAgIAAxkBAAIdImfEwUGyhfYEg4BN4sJ2I9VClCaKAAJCcgACgC0gSlE0KMnKTWDaNgQ",
            "CAACAgIAAxkBAAIdGmfEwRSvlYXb3YZm0iwzwsOmWIHaAAIobgAC1sUpSlRaSTRsb70rNgQ",
            "CAACAgIAAxkBAAIdJGfEwVA_XBS86vXJnCUTdy8wr-SsAAKcbAACt1cpSnA_gk48rn4BNgQ",
            "CAACAgIAAxkBAAIdJmfEwVeeXwLgLPryu3NZdlk0r-T2AALSaQACLoEoSghnO8fN8AnFNgQ");
    private static final List<String> sixthColumn = List.of(
            "CAACAgIAAxkBAAIdGGfEwQFQLfHbk3rWjVQ2aKPh0u9OAAKyZwAC9achSlnT1z_qj7z_NgQ",
            "CAACAgIAAxkBAAIdCGfEwJuyqV0gJ4AAAbzn4K39HgjnggAC8GQAAvIvKUpMiKVlnKdBeTYE",
            "CAACAgIAAxkBAAIdKGfEwWa6jnBSJ8DUcNfIiPdyI2HdAAImdQACTFspSqntSjPbN817NgQ",
            "CAACAgIAAxkBAAIdKmfEwWc0E-p2N_6o776xgGp4AjNMAAKKaAACw2QpSrJhaWNO6hWsNgQ",
            "CAACAgIAAxkBAAIdIGfEwTbg-appXDaMQlBDR0hnMiYKAAK1ZQACqlsoSm-z-aF9uN6BNgQ",
            "CAACAgIAAxkBAAIdLGfEwWijYt3-IItJv3ziyEFZKIudAAK2awACdiUhSqRuKy4Wd3LMNgQ",
            "CAACAgIAAxkBAAIdLmfEwWiUD7kLVURMtVTM4Fh5cGxWAALvbAACl8sgSvvTWnUl95HwNgQ");
    public static final List<List<String>> table = List.of(firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn);

    List<String> stickers = new ArrayList<>();

    public void addSticker(String sticker) {
        stickers.add(sticker);
    }
}
