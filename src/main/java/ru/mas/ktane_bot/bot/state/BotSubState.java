package ru.mas.ktane_bot.bot.state;

public enum BotSubState {
    // New Bomb
    BEGINCREATE,
    SERIALNUMBER,
    LIT_INDICATORS,
    UNLIT_INDICATORS,
    D_BATTERIES,
    AA_BATTERIES,
    BATTERIES_SLOTS,
    PORTS,
    PORT_HOLDERS_SLOTS,
    MODULES,
    // Memory
    MEMORY1,
    MEMORY2,
    MEMORY3,
    MEMORY4,
    MEMORY5,
    // Who's on first
    WHOSONFIRST1,
    WHOSONFIRST2,
    WHOSONFIRST3,
    // Password
    PASSWORD1,
    PASSWORD2,
    PASSWORD3,
    PASSWORD4;
}
