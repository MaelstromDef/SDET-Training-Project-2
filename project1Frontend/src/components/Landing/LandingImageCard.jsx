import { Children } from "react";
import styles from "./Picture.module.css";

const LandingImageCard = (props) => {
  let align = props.textAlign ?? 'left';

  return (
    <article className={styles.article}>
      <picture className={styles.picture}>
        <source media="(min-width: 0px)" srcSet={props.background} />
        <img src={props.background} alt="background" />
      </picture>
      <div className={styles.text} style={{textAlign: align}}>
        {props.children}
      </div>
    </article>
  );
};

export default LandingImageCard;