package lib.team3526.utils;

public class SwerveModuleOptions {
    public double offsetRad;
    public boolean absoluteEncoderInverted;
    public boolean driveMotorInverted;
    public boolean turningMotorInverted;
    public int absoluteEncoderID;
    public int driveMotorID;
    public int turningMotorID;
    public String name;
    public int driveMotorPwmPort;
    public int turningMotorPwmPort;

    public SwerveModuleOptions() {}
    public SwerveModuleOptions(double offsetRad, boolean absoluteEncoderInverted, int absoluteEncoderID, int driveMotorID, int turningMotorID, boolean driveMotorInverted, boolean turningMotorInverted, String name, int driveMotorPwmPort, int turningMotorPwmPort) {
        this.offsetRad = offsetRad;
        this.absoluteEncoderInverted = absoluteEncoderInverted;
        this.driveMotorInverted = driveMotorInverted;
        this.turningMotorInverted = turningMotorInverted;
        this.absoluteEncoderID = absoluteEncoderID;
        this.driveMotorID = driveMotorID;
        this.turningMotorID = turningMotorID;
        this.name = name;
        this.driveMotorPwmPort = driveMotorPwmPort;
        this.turningMotorPwmPort = turningMotorPwmPort;
    }

    public SwerveModuleOptions setOffsetRad(double offsetRad) {
        this.offsetRad = offsetRad;
        return this;
    }

    public SwerveModuleOptions setOffsetDeg(double offsetDeg) {
        this.offsetRad = Math.toRadians(offsetDeg);
        return this;
    }

    public SwerveModuleOptions setAbsoluteEncoderInverted(boolean absoluteEncoderInverted) {
        this.absoluteEncoderInverted = absoluteEncoderInverted;
        return this;
    }

    public SwerveModuleOptions setDriveMotorInverted(boolean driveMotorInverted) {
        this.driveMotorInverted = driveMotorInverted;
        return this;
    }

    public SwerveModuleOptions setTurningMotorInverted(boolean turningMotorInverted) {
        this.turningMotorInverted = turningMotorInverted;
        return this;
    }

    public SwerveModuleOptions setAbsoluteEncoderID(int absoluteEncoderID) {
        this.absoluteEncoderID = absoluteEncoderID;
        return this;
    }

    public SwerveModuleOptions setDriveMotorID(int driveMotorID) {
        this.driveMotorID = driveMotorID;
        return this;
    }

    public SwerveModuleOptions setTurningMotorID(int turningMotorID) {
        this.turningMotorID = turningMotorID;
        return this;
    }

    public SwerveModuleOptions setName(String name) {
        this.name = name;
        return this;
    }

    public SwerveModuleOptions setDriveMotorPwmPort(int driveMotorPwmPort) {
        this.driveMotorPwmPort = driveMotorPwmPort;
        return this;
    }

    public SwerveModuleOptions setTurningMotorPwmPort(int turningMotorPwmPort) {
        this.turningMotorPwmPort = turningMotorPwmPort;
        return this;
    }

    public double getOffsetRad() {
        return offsetRad;
    }

    public double getOffsetDeg() {
        return Math.toDegrees(offsetRad);
    }

    public boolean isAbsoluteEncoderInverted() {
        return absoluteEncoderInverted;
    }

    public boolean isDriveMotorInverted() {
        return driveMotorInverted;
    }

    public boolean isTurningMotorInverted() {
        return turningMotorInverted;
    }

    public int getAbsoluteEncoderID() {
        return absoluteEncoderID;
    }

    public int getDriveMotorID() {
        return driveMotorID;
    }

    public int getTurningMotorID() {
        return turningMotorID;
    }

    public String getName() {
        return name;
    }

    public int getDriveMotorPwmPort() {
        return driveMotorPwmPort;
    }

    public int getTurningMotorPwmPort() {
        return turningMotorPwmPort;
    }
}