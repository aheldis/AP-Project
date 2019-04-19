package land;

import card.Buff;
import card.Hero;
import card.Minion;
import card.Spell;
import counterAttack.Hybrid;
import counterAttack.Melee;
import counterAttack.Ranged;

class GetClass {
    private static final GetClass GET_CLASS = new GetClass();
    private GetClass() {

    }

    public GetClass getInstance() {
        return GET_CLASS;
    }

    public Class getClass(Object object) {
        if (object instanceof Buff)
            return Buff.class;
        if (object instanceof Hero)
            return Hero.class;
        if (object instanceof Minion)
            return Minion.class;
        if (object instanceof Spell)
            return Spell.class;
        if (object instanceof Hybrid)
            return Hybrid.class;
        if (object instanceof Melee)
            return Melee.class;
        if (object instanceof Ranged)
            return Ranged.class;
        return null;
    }
}
