import React, { useState, useEffect} from 'react';
import HeaderContainer from "./Header/HeaderContainer";
import TitleHelmet from './common/TitleHelmet';
import Loading from "./Loading/Loading";
import AuthContent from "./AuthContent/AuthContent";
import MainContent from "./MainContent/MainContent";

const App = (props) => {
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        if (!isLoaded){
            props.initializeAuth();
            setIsLoaded(true);
        }
    }, [isLoaded, props]);

    if (props.isLoading){
        return(
            <div>
                <HeaderContainer />
                <TitleHelmet title="Загрузка..." />
                <Loading />
            </div>
        );
    } else if (props.loggedUser){
        return (
            <div>
                <HeaderContainer />
                <TitleHelmet title="Основная страница" />
                <MainContent />
            </div>
        );
    } else {
        return (
            <div>
                <HeaderContainer />
                <TitleHelmet title="Авторизация"/>
                <AuthContent />
            </div>
        );
    }
}

export default App;
