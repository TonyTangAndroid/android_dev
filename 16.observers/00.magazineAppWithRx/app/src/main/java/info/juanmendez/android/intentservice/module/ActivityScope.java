package info.juanmendez.android.intentservice.module;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import javax.inject.Scope;

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {}
