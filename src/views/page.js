import React from 'react';
import PropTypes from '../utils/propTypes';

import bn from '../utils/bemnames';

import { Breadcrumb, BreadcrumbItem } from 'reactstrap';

import Typography from '../views/Typography';

const bem = bn.create('page');

const Page = ({
                  title,
                  breadcrumbs,
                  tag: Tag,
                  className,
                  children,
                  ...restProps
              }) => {
    const classes = bem.b('px-5', 'p-5', className);

    return (
        <Tag className={classes} {...restProps}>
            <div className={bem.e('header')}>
                {title && typeof title === 'string' ? (
                    <Typography type="h4" className={bem.e('title')}>
                        {title}
                    </Typography>
                ) : (
                    title
                )}
                {breadcrumbs && (
                    <Breadcrumb className={bem.e('breadcrumb')}>
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        {breadcrumbs.length &&
                        breadcrumbs.map(({ name, active }, index) => (
                            <BreadcrumbItem key={index} active={active}>
                                {name}
                            </BreadcrumbItem>
                        ))}
                    </Breadcrumb>
                )}
            </div>
            {children}
        </Tag>
    );
};

Page.propTypes = {
    tag: PropTypes.component,
    title: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
    className: PropTypes.string,
    children: PropTypes.node,
    breadcrumbs: PropTypes.arrayOf(
        PropTypes.shape({
            name: PropTypes.string,
            active: PropTypes.bool,
        })
    ),
};

Page.defaultProps = {
    tag: 'div',
    title: '',
};

export default Page;
