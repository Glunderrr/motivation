package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.R
import com.example.myapplication.di.util.BindKey

@Entity(tableName = "personal")
data class Personal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @BindKey(key = NAME_KEY)
    val name: String = "",

    @ColumnInfo(defaultValue = "0")
    @BindKey(key = AGE_KEY) val age: Int = 0,

    @ColumnInfo(defaultValue = "")
    @BindKey(GENDER_KEY) val gender: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(REGION_KEY) val region: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(PERSONALITY_KEY) val personality: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(EMOTIONAL_STATE_KEY) val emotionalState: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(USER_VALUES_KEY) val userValues: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(MAIN_GOAL_KEY) val mainGoal: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(FIELD_KEY) val field: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(CHALLENGES_KEY) val challenges: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(EXPERIENCE_LEVEL_KEY) val experienceLevel: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(TONE_KEY) val tone: String = "",

    @ColumnInfo(defaultValue = "")
    @BindKey(FORMAT_KEY) val format: String = "",

    @ColumnInfo(defaultValue = "0")
    @BindKey(MAX_LENGTH_KEY) val maxLength: Int = 0,

    @ColumnInfo(defaultValue = "0")
    @BindKey(ADDRESS_USER_KEY) val addressUser: Boolean = false
) {
    companion object {
        const val NAME_KEY = "name"
        const val AGE_KEY = "age"
        const val GENDER_KEY = "gender"
        const val REGION_KEY = "region"
        const val PERSONALITY_KEY = "personality"
        const val EMOTIONAL_STATE_KEY = "emotionalState"
        const val USER_VALUES_KEY = "userValues"
        const val MAIN_GOAL_KEY = "mainGoal"
        const val FIELD_KEY = "field"
        const val CHALLENGES_KEY = "challenges"
        const val EXPERIENCE_LEVEL_KEY = "experienceLevel"
        const val TONE_KEY = "tone"
        const val FORMAT_KEY = "format"
        const val MAX_LENGTH_KEY = "maxLength"
        const val ADDRESS_USER_KEY = "addressUser"
    }

    enum class Gender(val valueId: Int) {
        FEMALE(R.string.female),
        MALE(R.string.male),
        OTHER(R.string.other),
        NOT_SPECIFIED(R.string.not_specified)
    }

    enum class Region(val valueId: Int) {
        POLISSIA(R.string.polissia),
        VOLYN(R.string.volyn),
        PODILLIA(R.string.podillia),
        HALYCHYNA(R.string.halychyna),
        BUKOVYNA(R.string.bukovyna),
        ZAKARPATTIA(R.string.zakarpattia),
        SLOBOZHANSHCHYNA(R.string.slobozhanshchyna),
        DONBAS(R.string.donbas),
        ZAPORIZHZHIA(R.string.zaporizhzhia),
        TAVRIYA(R.string.tavriya),
        NADDNIPRYANSHCHYNA(R.string.naddnipryanshchyna),
        PRYCHORNOMORYA(R.string.prychornomorya),
        NOT_SPECIFIED(R.string.not_specified_region)
    }

    enum class Personality(val valueId: Int) {
        EXTROVERTED(R.string.extroverted),
        INTROVERTED(R.string.introverted),
        OPTIMISTIC(R.string.optimistic),
        PESSIMISTIC(R.string.pessimistic),
        AMBITIOUS(R.string.ambitious),
        LAID_BACK(R.string.laid_back),
        ANALYTICAL(R.string.analytical),
        EMOTIONAL(R.string.emotional),
        PRACTICAL(R.string.practical),
        CHARISMATIC(R.string.charismatic),
        ADVENTUROUS(R.string.adventurous),
        LOYAL(R.string.loyal),
        INDEPENDENT(R.string.independent)
    }

    enum class EmotionalState(val valueId: Int) {
        JOY(R.string.joy),
        EUPHORIA(R.string.euphoria),
        INSPIRATION(R.string.inspiration),
        ADMIRATION(R.string.admiration),
        CONFIDENCE(R.string.confidence),
        CALM(R.string.calm),
        SATISFACTION(R.string.satisfaction),
        GRATITUDE(R.string.gratitude),
        HARMONY(R.string.harmony),
        INTEREST(R.string.interest),
        SURPRISE(R.string.surprise),
        ANTICIPATION(R.string.anticipation),
        FOCUS(R.string.focus),
        INDIFFERENCE(R.string.indifference),
        REFLECTION(R.string.reflection),
        TIREDNESS(R.string.tiredness),
        NOSTALGIA(R.string.nostalgia),
        ANGER(R.string.anger),
        IRRITATION(R.string.irritation),
        ANXIETY(R.string.anxiety),
        FEAR(R.string.fear),
        SADNESS(R.string.sadness),
        LONELINESS(R.string.loneliness),
        APATHY(R.string.apathy),
        HOPELESSNESS(R.string.hopelessness)
    }

    enum class UserValues(val valueId: Int) {
        LOVE(R.string.love),
        FRIENDSHIP(R.string.friendship),
        FAMILY(R.string.family),
        TRUST(R.string.trust),
        HONESTY(R.string.honesty),
        JUSTICE(R.string.justice),
        DIGNITY(R.string.dignity),
        LOYALTY(R.string.loyalty),
        GRATITUDE(R.string.gratitude),
        MERCY(R.string.mercy),
        COMPASSION(R.string.compassion),
        RESPECT(R.string.respect),
        RESPONSIBILITY(R.string.responsibility),
        FREEDOM(R.string.freedom),
        EQUALITY(R.string.equality),
        INTEGRITY(R.string.integrity),
        KINDNESS(R.string.kindness),
        TOLERANCE(R.string.tolerance),
        SINCERITY(R.string.sincerity),
        SOLIDARITY(R.string.solidarity),
        MUTUAL_AID(R.string.mutual_aid),
        SECURITY(R.string.security),
        HEALTH(R.string.health),
        EDUCATION(R.string.education),
        WORK(R.string.work),
        SELF_REALIZATION(R.string.self_realization),
        BEAUTY(R.string.beauty),
        WISDOM(R.string.wisdom),
        FAITH(R.string.faith),
        HARMONY(R.string.harmony)
    }

    enum class Field(val valueId: Int) {
        SCIENCE(R.string.science),
        EDUCATION(R.string.education),
        MEDICINE(R.string.medicine),
        TECHNOLOGY(R.string.technology),
        INDUSTRY(R.string.industry),
        AGRICULTURE(R.string.agriculture),
        TRANSPORT(R.string.transport),
        BUSINESS(R.string.business),
        ART(R.string.art),
        MEDIA(R.string.media),
        LAW(R.string.law),
        POLITICS(R.string.politics),
        SOCIAL(R.string.social),
        SPORTS(R.string.sports),
        MILITARY(R.string.military),
        TOURISM(R.string.tourism),
        DESIGN(R.string.design),
        ECOLOGY(R.string.ecology),
        RELIGION(R.string.religion),
        HOUSEHOLD(R.string.household)
    }

    enum class Challenge(val valueId: Int) {
        LACK_OF_TIME(R.string.lack_of_time),
        HIGH_WORKLOAD(R.string.high_workload),
        FEAR_OF_STARTING(R.string.fear_of_starting),
        STRESS(R.string.stress),
        FINANCIAL_LIMITS(R.string.financial_limits),
        LOW_MOTIVATION(R.string.low_motivation),
        PROCRASTINATION(R.string.procrastination),
        UNCERTAIN_GOALS(R.string.uncertain_goals),
        FATIGUE(R.string.fatigue),
        CONFLICTS(R.string.conflicts),
        LACK_OF_SUPPORT(R.string.lack_of_support),
        INSUFFICIENT_EDUCATION(R.string.insufficient_education),
        LACK_OF_RESOURCES(R.string.lack_of_resources),
        CAREER_OBSTACLES(R.string.career_obstacles),
        TECHNICAL_ISSUES(R.string.technical_issues),
        HEALTH_ISSUES(R.string.health_issues),
        EMOTIONAL_CHALLENGES(R.string.emotional_challenges),
        SOCIAL_ISOLATION(R.string.social_isolation)
    }

    enum class ExperienceLevel(val valueId: Int) {
        BEGINNER(R.string.beginner),
        JUNIOR(R.string.junior),
        INTERMEDIATE(R.string.intermediate),
        EXPERIENCED(R.string.experienced),
        EXPERT(R.string.expert),
        MASTER(R.string.master)
    }

    enum class Tone(val valueId: Int) {
        FRIENDLY(R.string.friendly),
        FORMAL(R.string.formal),
        HUMOROUS(R.string.humorous),
        SERIOUS(R.string.serious),
        INSPIRING(R.string.inspiring)
    }

    enum class Format(val valueId: Int) {
        TEXT(R.string.text),
        SENTENCE(R.string.sentence),
        PARAGRAPH(R.string.paragraph),
        QUOTE(R.string.quote)
    }
}