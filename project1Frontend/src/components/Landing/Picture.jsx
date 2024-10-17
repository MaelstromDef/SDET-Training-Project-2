import styles from "./Picture.module.css";

const Picture = (props) => {
  return (
    <article className={styles.article}>
      <picture className={styles.picture}>
        <source media="(min-width: 0px)" srcSet={props.background} />
        <img src={props.background} alt="background" />
      </picture>
      <h1 className={styles.header}>Hello World</h1>
    </article>
  );
};

export default Picture;